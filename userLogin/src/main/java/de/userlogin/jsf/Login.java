package de.userlogin.jsf;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import de.userlogin.services.AuthService;

import java.io.Serializable;

//back bean for index.xhtml

@Named
@SessionScoped
public class Login implements Serializable {

    public static final String INDEX_XHTML_URL = "index.xhtml";
    public static final String USER_IS_LOGGED_IN = "user_is_logged_in";
    public static final String SECRET_XHTML_URL = "secret.xhtml";
    public static final String FACES_REDIRECT_TRUE_PARAMETER = "?faces-redirect=true";

    @Inject
    AuthService authService;

    // <input> elements on index.xhtml pageg are bound to these fields:
    private String emailAdress;
    private String password;

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    
     //Validate the entered user credentials.
     
    public String validateUsernamePassword() {
        boolean loggedIn = authService.validate(emailAdress, password);
        if (loggedIn) {
            getHttpSession().setAttribute(USER_IS_LOGGED_IN, "true");
            return SECRET_XHTML_URL + FACES_REDIRECT_TRUE_PARAMETER;
        } else {
            return "";
        }

    }

    
     //Get the current HttpSession object from the FacesContext (JSF framework).
     
    private HttpSession getHttpSession() {
        return (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(true);
    }

    
      // redirect to index page.
     
    public String logout() {
        getHttpSession().invalidate();
        return INDEX_XHTML_URL + FACES_REDIRECT_TRUE_PARAMETER;
    }
}
