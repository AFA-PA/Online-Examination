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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.afapa.exam.clientdata.SignedinRequest;
import org.afapa.exam.entity.User;
import org.afapa.exam.identity.UserIdentityStore;

/**
 *
 * @author AFAPA-group
 */
@Stateless
@Path("user")
public class UserFacadeREST extends AbstractFacade<User> {

    private static final Logger logger = Logger.getLogger("User Rest Log");

    @PersistenceContext(unitName = "WebApplicationTest3PU")
    private EntityManager em;
    @Inject
    UserIdentityStore idStr;

    @Override
    protected UserIdentityStore getIdentityStore() {
        return idStr;
    }

    public UserFacadeREST() {
        super(User.class);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, SignedinRequest<User> request) {
        CredentialValidationResult cvr = validateRequest(request);
        try {
            User user = find(id);
            if (cvr.getStatus().equals(Status.VALID) && cvr.getCallerPrincipal().getName().equals(user.getEmail())) {
                super.edit(user);
                return Response.status(Response.Status.ACCEPTED).entity(user).build();
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
        CredentialValidationResult cvr = validateRequest(new SignedinRequest<>(tokenString));
        try {
            if (cvr.getStatus().equals(Status.VALID)) {
                User user = find(id);
                if (cvr.getCallerPrincipal().getName().equals(user.getEmail())
                        || idStr.getCallerGroups(cvr).contains("admin")) {
                    super.edit(user);
                    return Response.status(Response.Status.ACCEPTED).build();
                }
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id, @QueryParam(value = "tokenString") String tokenString) {
        try {
            CredentialValidationResult cvr = validateRequest(new SignedinRequest<>(tokenString));
            if (cvr.getStatus().equals(CredentialValidationResult.Status.VALID)) {
                return Response.status(Response.Status.FOUND).entity(super.find(id)).build();
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Error: {0}", e.getMessage());
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        logger.log(Level.INFO, "getting all Users");
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
