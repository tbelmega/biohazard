package de.belmega.biohazard.auth.jsf;


import de.belmega.biohazard.auth.common.dto.UserDTO;
import de.belmega.biohazard.auth.service.AuthService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class LoginBean {

    @Inject
    AuthService authService;

    private String emailaddress;
    private String password;
    private String message;

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
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            session.setAttribute("username", emailaddress);
            return "index";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    "btnLogin",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect username or password",
                            "Please enter correct username and password."));
            return "null";
        }
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        return "login";
    }
}
