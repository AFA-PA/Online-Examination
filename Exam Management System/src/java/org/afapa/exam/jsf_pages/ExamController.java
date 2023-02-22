package org.afapa.exam.jsf_pages;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import lombok.Getter;
import lombok.Setter;
import org.afapa.exam.beans.ExamFacade;
import org.afapa.exam.entity.Choice;
import org.afapa.exam.entity.Exam;
import org.afapa.exam.entity.ExamTaken;
import org.afapa.exam.entity.Question;
import org.afapa.exam.jsf_pages.util.JsfUtil;
import org.afapa.exam.jsf_pages.util.PaginationHelper;

@Named("examController")
@SessionScoped
public class ExamController extends AbstractController implements Serializable {
    private static final Logger logger = Logger.getLogger("JSP_ExamCtrl");

    @PersistenceContext(unitName = "WebApplicationTest3PU")
    EntityManager em;
    @Resource
    UserTransaction utx;

    @Setter
    @Getter
    private Choice answer;

    private Exam current;
    private DataModel items = null;
    @EJB
    private org.afapa.exam.beans.ExamFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ExamController() {
    }

    public Exam getSelected() {
        if (current == null) {
            current = new Exam();
            selectedItemIndex = -1;
        }
        return current;
    }

    public long getNow() {
        return Instant.now().toEpochMilli();
    }

    public String select(Exam e) {
        current = e;
        return "take_exam";
    }

    public boolean isActive(Exam e) {
        return isStarted(e) && !isOver(e);
    }

    public boolean isStarted(Exam e) {
        logger.log(Level.INFO, "{0}", e);
        if (e == null) {
            return false;
        }
        return e.getStartTime().before(Timestamp.from(Instant.now()));
    }

    public boolean isOver(Exam e) {
        if (e == null) {
            return false;
        }
        return e.getStartTime().before(Timestamp.from(Instant.now().minusSeconds(e.getMinutesAllowed() * 60)));
    }

    public ExamTaken getTakenExam(Exam e) {
        ExamTaken et = new ExamTaken();
        for (ExamTaken et0 : getCurrentUser().getTakenExams()) {
            if (et0.getExam() == current) {
                et = et0;
                break;
            }
        }
        return et;
    }

    public String startTaking() {
        logger.log(Level.INFO, "user {0} starting to take {1}", new Object[]{getCurrentUser().getEmail(), current});
        logger.log(Level.INFO, "got {0} questions", current.getQuestions());
        if (getCurrentUser() == null) {
            return "login";
        }
        ExamTaken et = new ExamTaken();
        for (ExamTaken et0 : getCurrentUser().getTakenExams()) {
            if (et0.getExam() == current) {
                et = et0;
                break;
            }
        }

//        try {
//            current.setQuestions(new Db().getQuestions(current));
//            logger.log(Level.INFO, "get {0} questions", current.getQuestions().size());
//        } catch (SQLException ex) {
//            logger.log(Level.INFO, "{0}", ex.getMessage());
//        }
        Collections.shuffle(current.getQuestions());
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        et.setExam(current);
        et.setTaker(getCurrentUser());
        et.setAnswers(new ArrayList<>());
        try {
            utx.begin();
            em.persist(et);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        sessionMap.put("taking", et);
        sessionMap.put("cursor", 0);
        sessionMap.put("qnSize", et.getExam().getQuestions().size());
        return "beginTaking";
    }

    public void nextQuestion() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        int c = (int) sessionMap.get("cursor") + 1;
        int size = (int) sessionMap.get("qnSize");
        ExamTaken et = (ExamTaken) sessionMap.get("taking");
        et.getAnswers().add(answer);
        try {
            utx.begin();
            em.merge(et);
            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {
            Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (c < size) {
            sessionMap.put("cursor", c);
            logger.log(Level.INFO, "giving question {0}", c);
            return;
        }
        logger.log(Level.INFO, "end of questions");
    }

    public boolean hasNext() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        return (int) sessionMap.get("cursor") < (int) sessionMap.get("qnSize") - 1;

    }

    public boolean hasPrev() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        return (int) sessionMap.get("cursor") > 0;
    }

    public String submit() {
        nextQuestion();
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.remove("taking");
        sessionMap.remove("cursor");
        sessionMap.remove("qnSize");
        return "dashboard";
    }

    public void prevQuestion() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        int c = (int) sessionMap.get("cursor") - 1;
        if (c >= 0) {
            sessionMap.put("cursor", c);
            logger.log(Level.INFO, "giving question {0}", c);
            return;
        }
        logger.log(Level.INFO, "beggining of questions");
    }

    public Question getCurrentQuestion() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        int c = (int) sessionMap.get("cursor");
        ExamTaken et = (ExamTaken) sessionMap.get("taking");
        Question q = et.getExam().getQuestions().get(c);
        return q;
    }

    private ExamFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Exam) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Exam();
        current.setId(0l);
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ExamCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Exam) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ExamUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Exam) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ExamDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Exam getExam(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Exam.class)
    public static class ExamControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExamController controller = (ExamController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "examController");
            return controller.getExam(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Exam) {
                Exam o = (Exam) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Exam.class.getName());
            }
        }
    }

    public void newChoice() {
        Choice ch = new Choice();
    }

    public void newQuestion() {
        Question q = new Question();
        q.setExam(current);
        Choice ch = new Choice();
        ch.setQuestion(q);
        q.setChoices(new ArrayList<Choice>());
        q.getChoices().add(ch);
        current.getQuestions().add(q);
    }

    public void dropQuestion(Question q) {
        current.getQuestions().remove(q);
    }

    public void newChoice(Question q) {
        Choice ch = new Choice();
        ch.setQuestion(q);
        q.getChoices().add(ch);
    }

    public void dropChoice(Question q, Choice c) {
        q.getChoices().remove(c);
    }

}
