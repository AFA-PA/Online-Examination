package org.afapa.exam.jsf_pages;

import javax.faces.context.FacesContext;
import org.afapa.exam.entity.User;

/**
 *
 * @author ketij
 */
public abstract class AbstractController {

    public User getCurrentUser() {
        return (User) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("user");
    }

    public boolean isLoggedin() {
        Object user = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("user");
        return user != null;
    }

}
