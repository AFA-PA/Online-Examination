/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import org.afapa.exam.entity.Department;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import org.afapa.exam.controllers.exceptions.NonexistentEntityException;
import org.afapa.exam.controllers.exceptions.RollbackFailureException;
import org.afapa.exam.entity.Organization;

/**
 *
 * @author ketij
 */
public class OrganizationJpaController implements Serializable {

    public OrganizationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Organization organization) throws RollbackFailureException, Exception {
        if (organization.getDepartments() == null) {
            organization.setDepartments(new HashSet<Department>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Set<Department> attachedDepartments = new HashSet<Department>();
            for (Department departmentsDepartmentToAttach : organization.getDepartments()) {
                departmentsDepartmentToAttach = em.getReference(departmentsDepartmentToAttach.getClass(), departmentsDepartmentToAttach.getId());
                attachedDepartments.add(departmentsDepartmentToAttach);
            }
            organization.setDepartments(attachedDepartments);
            em.persist(organization);
            for (Department departmentsDepartment : organization.getDepartments()) {
                Organization oldOrganizationOfDepartmentsDepartment = departmentsDepartment.getOrganization();
                departmentsDepartment.setOrganization(organization);
                departmentsDepartment = em.merge(departmentsDepartment);
                if (oldOrganizationOfDepartmentsDepartment != null) {
                    oldOrganizationOfDepartmentsDepartment.getDepartments().remove(departmentsDepartment);
                    oldOrganizationOfDepartmentsDepartment = em.merge(oldOrganizationOfDepartmentsDepartment);
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

    public void edit(Organization organization) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organization persistentOrganization = em.find(Organization.class, organization.getId());
            Set<Department> departmentsOld = persistentOrganization.getDepartments();
            Set<Department> departmentsNew = organization.getDepartments();
            Set<Department> attachedDepartmentsNew = new HashSet<Department>();
            for (Department departmentsNewDepartmentToAttach : departmentsNew) {
                departmentsNewDepartmentToAttach = em.getReference(departmentsNewDepartmentToAttach.getClass(), departmentsNewDepartmentToAttach.getId());
                attachedDepartmentsNew.add(departmentsNewDepartmentToAttach);
            }
            departmentsNew = attachedDepartmentsNew;
            organization.setDepartments(departmentsNew);
            organization = em.merge(organization);
            for (Department departmentsOldDepartment : departmentsOld) {
                if (!departmentsNew.contains(departmentsOldDepartment)) {
                    departmentsOldDepartment.setOrganization(null);
                    departmentsOldDepartment = em.merge(departmentsOldDepartment);
                }
            }
            for (Department departmentsNewDepartment : departmentsNew) {
                if (!departmentsOld.contains(departmentsNewDepartment)) {
                    Organization oldOrganizationOfDepartmentsNewDepartment = departmentsNewDepartment.getOrganization();
                    departmentsNewDepartment.setOrganization(organization);
                    departmentsNewDepartment = em.merge(departmentsNewDepartment);
                    if (oldOrganizationOfDepartmentsNewDepartment != null && !oldOrganizationOfDepartmentsNewDepartment.equals(organization)) {
                        oldOrganizationOfDepartmentsNewDepartment.getDepartments().remove(departmentsNewDepartment);
                        oldOrganizationOfDepartmentsNewDepartment = em.merge(oldOrganizationOfDepartmentsNewDepartment);
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
                Long id = organization.getId();
                if (findOrganization(id) == null) {
                    throw new NonexistentEntityException("The organization with id " + id + " no longer exists.");
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
            Organization organization;
            try {
                organization = em.getReference(Organization.class, id);
                organization.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The organization with id " + id + " no longer exists.", enfe);
            }
            Set<Department> departments = organization.getDepartments();
            for (Department departmentsDepartment : departments) {
                departmentsDepartment.setOrganization(null);
                departmentsDepartment = em.merge(departmentsDepartment);
            }
            em.remove(organization);
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

    public List<Organization> findOrganizationEntities() {
        return findOrganizationEntities(true, -1, -1);
    }

    public List<Organization> findOrganizationEntities(int maxResults, int firstResult) {
        return findOrganizationEntities(false, maxResults, firstResult);
    }

    private List<Organization> findOrganizationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Organization as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Organization findOrganization(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Organization.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrganizationCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Organization as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
