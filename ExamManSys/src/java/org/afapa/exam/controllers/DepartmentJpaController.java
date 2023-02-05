/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import org.afapa.exam.entity.Organization;
import org.afapa.exam.entity.Course;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import org.afapa.exam.controllers.exceptions.NonexistentEntityException;
import org.afapa.exam.controllers.exceptions.RollbackFailureException;
import org.afapa.exam.entity.Department;

/**
 *
 * @author ketij
 */
public class DepartmentJpaController implements Serializable {

    public DepartmentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Department department) throws RollbackFailureException, Exception {
        if (department.getCourses() == null) {
            department.setCourses(new ArrayList<Course>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organization organization = department.getOrganization();
            if (organization != null) {
                organization = em.getReference(organization.getClass(), organization.getId());
                department.setOrganization(organization);
            }
            List<Course> attachedCourses = new ArrayList<Course>();
            for (Course coursesCourseToAttach : department.getCourses()) {
                coursesCourseToAttach = em.getReference(coursesCourseToAttach.getClass(), coursesCourseToAttach.getId());
                attachedCourses.add(coursesCourseToAttach);
            }
            department.setCourses(attachedCourses);
            em.persist(department);
            if (organization != null) {
                organization.getDepartments().add(department);
                organization = em.merge(organization);
            }
            for (Course coursesCourse : department.getCourses()) {
                Department oldDepartmentOfCoursesCourse = coursesCourse.getDepartment();
                coursesCourse.setDepartment(department);
                coursesCourse = em.merge(coursesCourse);
                if (oldDepartmentOfCoursesCourse != null) {
                    oldDepartmentOfCoursesCourse.getCourses().remove(coursesCourse);
                    oldDepartmentOfCoursesCourse = em.merge(oldDepartmentOfCoursesCourse);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Department department) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Department persistentDepartment = em.find(Department.class, department.getId());
            Organization organizationOld = persistentDepartment.getOrganization();
            Organization organizationNew = department.getOrganization();
            List<Course> coursesOld = persistentDepartment.getCourses();
            List<Course> coursesNew = department.getCourses();
            if (organizationNew != null) {
                organizationNew = em.getReference(organizationNew.getClass(), organizationNew.getId());
                department.setOrganization(organizationNew);
            }
            List<Course> attachedCoursesNew = new ArrayList<Course>();
            for (Course coursesNewCourseToAttach : coursesNew) {
                coursesNewCourseToAttach = em.getReference(coursesNewCourseToAttach.getClass(), coursesNewCourseToAttach.getId());
                attachedCoursesNew.add(coursesNewCourseToAttach);
            }
            coursesNew = attachedCoursesNew;
            department.setCourses(coursesNew);
            department = em.merge(department);
            if (organizationOld != null && !organizationOld.equals(organizationNew)) {
                organizationOld.getDepartments().remove(department);
                organizationOld = em.merge(organizationOld);
            }
            if (organizationNew != null && !organizationNew.equals(organizationOld)) {
                organizationNew.getDepartments().add(department);
                organizationNew = em.merge(organizationNew);
            }
            for (Course coursesOldCourse : coursesOld) {
                if (!coursesNew.contains(coursesOldCourse)) {
                    coursesOldCourse.setDepartment(null);
                    coursesOldCourse = em.merge(coursesOldCourse);
                }
            }
            for (Course coursesNewCourse : coursesNew) {
                if (!coursesOld.contains(coursesNewCourse)) {
                    Department oldDepartmentOfCoursesNewCourse = coursesNewCourse.getDepartment();
                    coursesNewCourse.setDepartment(department);
                    coursesNewCourse = em.merge(coursesNewCourse);
                    if (oldDepartmentOfCoursesNewCourse != null && !oldDepartmentOfCoursesNewCourse.equals(department)) {
                        oldDepartmentOfCoursesNewCourse.getCourses().remove(coursesNewCourse);
                        oldDepartmentOfCoursesNewCourse = em.merge(oldDepartmentOfCoursesNewCourse);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = department.getId();
                if (findDepartment(id) == null) {
                    throw new NonexistentEntityException("The department with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Department department;
            try {
                department = em.getReference(Department.class, id);
                department.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The department with id " + id + " no longer exists.", enfe);
            }
            Organization organization = department.getOrganization();
            if (organization != null) {
                organization.getDepartments().remove(department);
                organization = em.merge(organization);
            }
            List<Course> courses = department.getCourses();
            for (Course coursesCourse : courses) {
                coursesCourse.setDepartment(null);
                coursesCourse = em.merge(coursesCourse);
            }
            em.remove(department);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Department> findDepartmentEntities() {
        return findDepartmentEntities(true, -1, -1);
    }

    public List<Department> findDepartmentEntities(int maxResults, int firstResult) {
        return findDepartmentEntities(false, maxResults, firstResult);
    }

    private List<Department> findDepartmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Department as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Department findDepartment(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Department.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartmentCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Department as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
