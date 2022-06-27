/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.service;

import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.ws.rs.core.Application;
import org.afapa.exam.identity.Pbkdf2PasswordHasher;

/**
 *
 * @author AFAPA-group
 */
@BasicAuthenticationMechanismDefinition(realmName = "dbIdst")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:app/examDataSource",
        callerQuery = "select TOKEN from USERCREDENTIAL where TOKEN = ?",
        hashAlgorithm = Pbkdf2PasswordHasher.class,
        priorityExpression = "#{100}",
        hashAlgorithmParameters = {
            "Pbkdf2PasswordHash.Iterations=3072",
            "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
            "Pbkdf2PasswordHash.SaltSizeBytes=64"
        }
)
@Named
@ApplicationScoped
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(org.afapa.exam.service.AnswerFacadeREST.class);
        resources.add(org.afapa.exam.service.AuthRest.class);
        resources.add(org.afapa.exam.service.ChoiceFacadeREST.class);
        resources.add(org.afapa.exam.service.CourseFacadeREST.class);
        resources.add(org.afapa.exam.service.DepartmentFacadeREST.class);
        resources.add(org.afapa.exam.service.ExamFacadeREST.class);
        resources.add(org.afapa.exam.service.OrganizationFacadeREST.class);
        resources.add(org.afapa.exam.service.OrganizationalUnitFacadeREST.class);
        resources.add(org.afapa.exam.service.QuestionFacadeREST.class);
        resources.add(org.afapa.exam.service.TakeExamFacadeREST.class);
        resources.add(org.afapa.exam.service.UserFacadeREST.class);
    }

}
