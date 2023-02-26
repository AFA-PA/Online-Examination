/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.service;

import java.sql.Timestamp;
import java.time.Instant;
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
import org.afapa.exam.entity.Exam;
import org.afapa.exam.entity.Question;
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
@Path("exam")
public class ExamFacadeREST extends AbstractFacade<Exam> {

    private static final Logger logger = Logger.getLogger("Organization Rest Log");

    @PersistenceContext(unitName = "WebApplicationTest3PU")
    private EntityManager em;
    @Inject
    UserIdentityStore idStr;

    @Override
    protected UserIdentityStore getIdentityStore() {
        return idStr;
    }

    public ExamFacadeREST() {
        super(Exam.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(SignedinRequest<Exam> request) {
        String[] tokens = request.getTokenString().split(":");
        User user = getEntityManager().createNamedQuery("getUserById", User.class)
                .setParameter("id", Long.parseLong(tokens[0])).getSingleResult();
        CredentialValidationResult cvr = idStr.validate(new UserCredential(user,
                new Token(user, tokens[1])));
        if (cvr.getStatus().equals(CredentialValidationResult.Status.VALID)) {
            Exam exam = request.getEntity();

            if (Privilege.isPermittedTo(idStr.getPrivilegesOn(cvr, exam.getRegistration().getCourse()),
                    exam.getRegistration().getCourse(), Privilege.CREATE, true)) {
                super.create(exam);
                return Response.status(Response.Status.CREATED).entity(exam).build();
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
    public Response edit(@PathParam("id") Long id, SignedinRequest<Exam> request) {
        CredentialValidationResult cvr = validateRequest(request);
        try {
            Exam exam = em.createNamedQuery("getExamById", Exam.class)
                    .setParameter("id", id).getSingleResult();
            if (cvr.getStatus().equals(Status.VALID) && Privilege.isPermittedTo(
                    idStr.getPrivilegesOn(cvr, exam.getRegistration().getCourse()), exam.getRegistration().getCourse(), Privilege.EDIT, true)) {
                super.edit(exam);
                return Response.status(Response.Status.ACCEPTED).entity(exam).build();
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") Long id, @QueryParam(value = "tokenString") String tokenString) {
        CredentialValidationResult cvr = validateRequest(new SignedinRequest<>(tokenString));
        try {
            Exam exam = em.createNamedQuery("getExamById", Exam.class)
                    .setParameter("id", id).getSingleResult();
            if (cvr.getStatus().equals(Status.VALID) && Privilege.isPermittedTo(
                    idStr.getPrivilegesOn(cvr, exam.getRegistration().getCourse()), exam.getRegistration().getCourse(), Privilege.DELETE, true)) {
                super.remove(exam);
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id, @QueryParam("tokenString") String tokenString) {
        CredentialValidationResult cvr = validateRequest(new SignedinRequest<>(tokenString));
        try {
            if (cvr.getStatus().equals(Status.VALID)) {
                Exam exam = em.createNamedQuery("getExamById", Exam.class)
                        .setParameter("id", id).getSingleResult();
                if (exam.getStartTime().before(new Timestamp(Instant.now().toEpochMilli() + exam.getMinutesAllowed() * 60000))) {
                    if (!Privilege.isPermittedTo(idStr.getPrivilegesOn(cvr, exam.getRegistration().getCourse()),
                            exam.getRegistration().getCourse(), (2 | 4 | 8 | 16), false)) {
                        return Response.status(Response.Status.BAD_REQUEST).build();
                    }
                }
                return Response.status(Response.Status.OK).entity(exam).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{id}/questions")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getQuestions(@PathParam("id") Long id, @QueryParam("tokenString") String tokenString) {
        CredentialValidationResult cvr = validateRequest(new SignedinRequest(tokenString));
        try {
            if (cvr.getStatus().equals(Status.VALID)) {
                Exam exam = find(id);
                if (exam.getStartTime().before(new Timestamp(Instant.now().toEpochMilli() + exam.getMinutesAllowed() * 60000))) {
                    if (!Privilege.isPermittedTo(idStr.getPrivilegesOn(cvr, exam.getRegistration().getCourse()),
                            exam.getRegistration().getCourse(), (2 | 4 | 8 | 16), false)) {
                        return Response.status(Response.Status.BAD_REQUEST).build();
                    }
                }
                return Response.status(Response.Status.FOUND).entity(exam.getQuestions()).build();
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

    @POST
    @Path("{id}/questions")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addQuestion(@PathParam("id") Long id, SignedinRequest<Question> request) {
        CredentialValidationResult cvr = validateRequest(request);
        try {
            if (cvr.getStatus().equals(Status.VALID)) {
                Question question = request.getEntity();
                if (!Privilege.isPermittedTo(idStr.getPrivilegesOn(cvr, question.getExam().getRegistration().getCourse()),
                        question.getExam().getRegistration().getCourse(), Privilege.EDIT, true)) {
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
                em.persist(question);
                return Response.status(Response.Status.CREATED).entity(question).build();
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
    public Response findAll(@QueryParam(value = "tokenString") String tokenString) {
        try {
            CredentialValidationResult cvr = validateRequest(new SignedinRequest<>(tokenString));
            if (cvr.getStatus().equals(Status.VALID)) {
                if (idStr.getCallerGroups(cvr).contains("admin")) {
                    List<Exam> orgs = super.findAll();
                    return Response.status(Response.Status.FOUND).entity(orgs).build();
                }
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Error: {0}", e.getMessage());
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findRange(@PathParam("from") Integer from, @PathParam("to") Integer to, @QueryParam(value = "tokenString") String tokenString) {
        try {
            CredentialValidationResult cvr = validateRequest(new SignedinRequest<>(tokenString));
            if (cvr.getStatus().equals(Status.VALID)) {
                if (idStr.getCallerGroups(cvr).contains("admin")) {
                    List<Exam> orgs = super.findRange(new int[]{from, to});
                    return Response.status(Response.Status.FOUND).entity(orgs).build();
                }
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Error: {0}", e.getMessage());
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countREST(@QueryParam(value = "tokenString") String tokenString) {
        try {
            CredentialValidationResult cvr = validateRequest(new SignedinRequest<>(tokenString));
            if (cvr.getStatus().equals(Status.VALID)) {
                if (idStr.getCallerGroups(cvr).contains("admin")) {
                    return Response.status(Response.Status.FOUND).entity(String.valueOf(super.count())).build();
                }
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Error: {0}", e.getMessage());
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
