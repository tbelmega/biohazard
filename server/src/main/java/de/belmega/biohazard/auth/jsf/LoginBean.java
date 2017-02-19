package de.belmega.biohazard.auth.jsf;


import de.belmega.biohazard.auth.common.dto.UserDTO;
import de.belmega.biohazard.auth.service.AuthService;
import de.belmega.biohazard.server.jsf.WorldListBean;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    public static final String ATTRIBUTE_USER = "user";
    public static final String LOGIN_PAGE = "login";

    @Inject
    AuthService authService;

    private String emailaddress;
    private String password;
    private String message;

    public LoginBean() {
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String validateUsernamePassword() {
        UserDTO user = new UserDTO(emailaddress, password);
        boolean valid = authService.validate(user);
        if (valid) {
            HttpSession session = getHttpSession();
            session.setAttribute(ATTRIBUTE_USER, user);
            return WorldListBean.INDEX_PAGE_REDIRECT;
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect username or password",
                            "Please enter correct username and password."));
            return null;
        }
    }

    public String logout() {
        getHttpSession().invalidate();
        return LOGIN_PAGE;
    }

    public Boolean getLoggedIn() {
        HttpSession session = getHttpSession();
        Object username = session.getAttribute(ATTRIBUTE_USER);
        boolean sessionHasLoggedInUser = username != null;
        return sessionHasLoggedInUser;
    }

    private HttpSession getHttpSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public String getGreeting() {
        UserDTO user = (UserDTO) getHttpSession().getAttribute(ATTRIBUTE_USER);
        return "Hello, " + user.getName() + ". ";
    }
}
