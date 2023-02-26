/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.afapa.exam.clientdata.SignedinRequest;
import org.afapa.exam.entity.Organization;
import org.afapa.exam.entity.User;
import org.afapa.exam.identity.Privilege;
import org.afapa.exam.identity.Token;
import org.afapa.exam.identity.UserCredential;
import org.afapa.exam.identity.UserIdentityStore;

/**
 *
 * @author AFAPA-group
 */
@Stateless
@Path("organization")
//@RolesAllowed({"signedin"})
public class OrganizationFacadeREST extends AbstractFacade<Organization> {

    private static final Logger logger = Logger.getLogger("Organization Rest Log");

    @PersistenceContext(unitName = "WebApplicationTest3PU")
    private EntityManager em;

    @Inject
    UserIdentityStore idStr;

    @Override
    protected UserIdentityStore getIdentityStore() {
        return idStr;
    }

    public OrganizationFacadeREST() {
        super(Organization.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"admin"})
    public Response create(SignedinRequest<Organization> request) {
        String[] tokens = request.getTokenString().split(":");
        User user = getEntityManager().createNamedQuery("getUserById", User.class)
                .setParameter("id", Long.parseLong(tokens[0])).getSingleResult();
        CredentialValidationResult cvr = idStr.validate(new UserCredential(user,
                new Token(user, tokens[1])));
        if (cvr.getStatus().equals(Status.VALID)) {
            if (idStr.getCallerGroups(cvr).contains("admin")) {
                Organization organization = request.getEntity();
                if (organization.getCreatedBy() == null) {
                    logger.log(Level.INFO, "setting createdBy for {0} to {1}", new Object[]{
                        organization.getName(), cvr.getCallerDn()
                    });
                    organization.setCreatedBy(user);
                }

                Privilege privilege = new Privilege(user, organization, (short) (Privilege.PRESIDENCY + Privilege.PERMIT));
                super.create(organization);
                em.persist(privilege);
                return Response.status(Response.Status.CREATED).entity(organization).build();
            }
            logger.log(Level.INFO, "Unautherized attempt to create an organization has been forbidden {0}", cvr.getCallerGroups());
            return Response.status(Response.Status.UNAUTHORIZED).build();

        }
        logger.log(Level.INFO, "validation faild for userId: {0}", tokens[0]);
        return Response.status(Response.Status.NETWORK_AUTHENTICATION_REQUIRED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, SignedinRequest<Organization> request) {
        CredentialValidationResult cvr = validateRequest(request);
        try {
            Organization org = find(id);
//            em.createNamedQuery("Organization.findById", Organization.class).setParameter("id", id).getSingleResult();
            if (cvr.getStatus().equals(Status.VALID) && Privilege.isPermittedTo(
                    idStr.getPrivilegesOn(cvr, org), org, Privilege.EDIT, true)) {
                super.edit(org);
                return Response.status(Response.Status.ACCEPTED).entity(org).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id, @QueryParam(value = "tokenString") String tokenString) {
        try {
            CredentialValidationResult cvr = validateRequest(new SignedinRequest<>(tokenString));
            Organization org = find(id);
//            em.createNamedQuery("Organization.findById", Organization.class).setParameter("id", id).getSingleResult();
            if (cvr.getStatus().equals(Status.VALID) && Privilege.isPermittedTo(
                    idStr.getPrivilegesOn(cvr, org), org, Privilege.PRESIDENCY + Privilege.DELETE, true)) {
                super.remove(org);
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            logger.log(Level.INFO, "error: {0}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     *
     * @param id
     * @param tokenString
     * @return
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id, @QueryParam("tokenString") String tokenString) {
        CredentialValidationResult cvr = validateRequest(new SignedinRequest<>(tokenString));
        try {
            Organization org = find(id);
//            em.createNamedQuery("getOrganizationById", Organization.class).setParameter("id", id).getResultList().get(0);
            if (cvr.getStatus().equals(Status.VALID)) {
                return Response.status(Response.Status.FOUND).entity(org).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            logger.log(Level.INFO, "{0}\n", new Object[]{e.getMessage()});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{id}/departments")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDepartments(@PathParam("id") Long id, @QueryParam("tokenString") String tokenString) {
        CredentialValidationResult cvr = validateRequest(new SignedinRequest(tokenString));
        try {
//em.createNamedQuery("Organization.findById", Organization.class).setParameter("id", id).getSingleResult();
            Organization org = find(id);
            if (cvr.getStatus().equals(Status.VALID)) {
                return Response.status(Response.Status.FOUND).entity(org.getDepartments()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (NoResultException e) {
            logger.log(Level.INFO, "organization with id {0} not found", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll(@QueryParam("tokenString") String tokenString) {
        CredentialValidationResult cvr = validateRequest(new SignedinRequest(tokenString));
        try {
            if (cvr.getStatus().equals(Status.VALID)) {
//                if (idStr.getCallerGroups(cvr).contains("admin")) {
                List<Organization> orgs = super.findAll(); //em.createNamedQuery("Organization.findByAll", Organization.class).getResultList();
                return Response.status(Response.Status.FOUND).entity(orgs).build();
//                }
//                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findRange(@PathParam("from") Integer from, @PathParam("to") Integer to, @QueryParam("tokenString") String tokenString) {
        CredentialValidationResult cvr = validateRequest(new SignedinRequest(tokenString));
        try {
            if (cvr.getStatus().equals(Status.VALID) && idStr.getCallerGroups(cvr).contains("admin")) {
                List<Organization> orgs = super.findRange(new int[]{from, to});
                return Response.status(Response.Status.FOUND).entity(orgs).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countREST(@QueryParam("tokenString") String tokenString) {
        CredentialValidationResult cvr = validateRequest(new SignedinRequest(tokenString));
        try {
            if (cvr.getStatus().equals(Status.VALID) && idStr.getCallerGroups(cvr).contains("admin")) {
                return Response.status(Response.Status.FOUND).entity(String.valueOf(super.count())).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public Organization find(Object id) {
        logger.log(Level.INFO, "searching for org id: {0}", (long) id);
        for (Organization o : super.findAll()) {
            logger.log(Level.INFO, "checking org id: {0}", (long) o.getId());
            if (o.getId().equals((long) id)) {
                return o;
            }
        }
        throw new NoResultException();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
