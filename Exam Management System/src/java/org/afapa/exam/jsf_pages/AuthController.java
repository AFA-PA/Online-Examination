package org.afapa.exam.jsf_pages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import lombok.Getter;
import lombok.Setter;
import org.afapa.exam.entity.CourseRegistration;
import org.afapa.exam.entity.Exam;
import org.afapa.exam.entity.User;
import org.afapa.exam.identity.UserCredential;
import org.afapa.exam.identity.UserIdentityStore;

@Named("authController")
@SessionScoped
public class AuthController extends AbstractController implements Serializable {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    @Inject
    UserIdentityStore idStr;

    @Getter
    @Setter
    private User newUser;

    List<Exam> exams;
    @Getter
    @Setter
    private String password;
    private String message;
    private static final Logger logger = Logger.getLogger("JSP_Auth");

    public AuthController() {
        newUser = new User();
    }

//    public User getSelected(){
//        return
//    }
    public String login() {
        logger.log(Level.INFO, "logging in: {0}", newUser.getEmail());
        try {
            User user = (User) em.createNamedQuery("getUserByEmail").setParameter("email", newUser.getEmail()).getSingleResult();
            UserCredential uc = new UserCredential(user, password);
            CredentialValidationResult cvr = idStr.validate(uc);
            if (cvr.getStatus() == CredentialValidationResult.Status.VALID) {
                logger.log(Level.INFO, "Signed in a user with email: {0}", newUser.getEmail());
                password = null;
                FacesContext.getCurrentInstance()
                        .getExternalContext().getSessionMap().put("user", user);
                newUser = user;
                return "loggedin";
            } else {
                newUser = new User();
                message = "invalid credential";
                return "invalid";
            }
        } catch (NoResultException e) {
            logger.log(Level.INFO, "user with {0} does not exist", newUser.getEmail());
        }
        newUser = new User();
        return "";
    }

    public String register() {
        logger.log(Level.INFO, "signup: {0}", newUser.getEmail());
        if (em.createNamedQuery("getUserByEmail")
                .setParameter("email", newUser.getEmail()).setMaxResults(1).getResultList().isEmpty()) {
            try {
                User user = new User(newUser.getEmail(), newUser.getFirstName(), newUser.getLastName(), newUser.getPhoneNumber());
                UserCredential uc = new UserCredential(user, password);
                logger.log(Level.INFO, "begginin transaction...");
                utx.begin();
                em.persist(user);
                em.persist(uc);
                utx.commit();
                return "login";
            } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
                logger.log(Level.SEVERE, "transaction error: {0}", ex.getMessage());
            }

        } else {
            message = "email already exist";
        }
        return "";
    }

    public String logout() {
        FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().remove("user");
        newUser = new User();
        return "../index.xhtml";
    }

    public String getMessage() {
        String tmp = message;
        message = null;
        return tmp;
    }

    public int getRegCount() {
        try {
            return getCurrentUser().getCourseRegistrations().size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public String prepareDashboard() {
        if (!isLoggedin()) {
            ExamController ec = (ExamController) FacesContext.getCurrentInstance().getELContext().getContext(ExamController.class);
            logger.log(Level.INFO, "{0}", ec);
            return "login";
        }
        return "dashboard";
    }

    public String prepareSignup() {
        newUser = new User();
        return "signup";
    }

    public String prepareLogin() {
        newUser = new User();
        return "login";
    }

    public List<Exam> getExams() {
        User current = getCurrentUser();
        if (current == null) {
            return new ArrayList<>();
        }
        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            cq.select(cq.from(Exam.class));
            if (exams == null) {
            ArrayList<Long> ids = new ArrayList<>();
            current.getCourseRegistrations().forEach((CourseRegistration cr) -> {
                ids.add(cr.getId());
                logger.log(Level.INFO, "{0} regs", ids.size());
            });

            exams = em.createQuery("SELECT e FROM Exam e WHERE e.registration.id IN :reg_ids", Exam.class)
                        .setParameter("reg_ids", ids).getResultList();
            }
//            if (exams.size() < 1) {
//                utx.begin();
//                exams.forEach((Exam e) -> {
//                    em.merge(e);
//                });
//                utx.commit();}
//            exams = new Db().getAllExams();

        } catch (Exception ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exams;
    }

    public void resetExams() {
        exams = null;
    }

    public void setExams(CourseRegistration reg) {
        exams = em.createQuery("SELECT e FROM Exam e WHERE e.registration.id=:reg_id", Exam.class)
                .setParameter("reg_id", reg.getId()).getResultList();
    }

    public List<Exam> getExams(CourseRegistration reg) {
        if (reg == null) {
            return getExams();
        }
        try {
            logger.log(Level.SEVERE, "{0}", reg);
            exams = em.createQuery("SELECT e FROM Exam e WHERE e.registration.id=:reg_id", Exam.class).
                    setParameter("reg_id", reg.getId()).getResultList();
//            exams = new Db().getExams(reg.getId());
        } catch (Exception ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        FacesContext.getCurrentInstance().renderResponse();
        return exams;
    }

    @Getter
    @Setter
    public class Auth {
        private String email = "";
        private String firstName = "";
        private String lastName = "";
        private String phoneNumber = "";
        private String password = "";
        private String passwordConfirm = "";
    }
}
