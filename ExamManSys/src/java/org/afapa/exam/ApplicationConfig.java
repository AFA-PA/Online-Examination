/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam;

import java.util.Set;

/**
 *
 * @author ketij
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        // addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(org.afapa.exam.UserResource.class);
        resources.add(org.afapa.exam.service.AnswerFacadeREST.class);
        resources.add(org.afapa.exam.service.ChoiseFacadeREST.class);
        resources.add(org.afapa.exam.service.CourseFacadeREST.class);
        resources.add(org.afapa.exam.service.DepartmentFacadeREST.class);
        resources.add(org.afapa.exam.service.ExamFacadeREST.class);
        resources.add(org.afapa.exam.service.ExamTakenFacadeREST.class);
        resources.add(org.afapa.exam.service.OrganizationFacadeREST.class);
        resources.add(org.afapa.exam.service.OrganizationalUnitFacadeREST.class);
        resources.add(org.afapa.exam.service.QuestionFacadeREST.class);
        resources.add(org.afapa.exam.service.UserFacadeREST.class);
    }

}
