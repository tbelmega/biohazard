package de.belmega.biohazard.auth.jsf;

import de.belmega.biohazard.auth.common.dto.UserDTO;
import de.belmega.biohazard.auth.service.UserManagementService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UserListBean {

    @Inject
    UserManagementService userManagementService;

    public Object getFilter() {
        return null;
    }

    public List<UserDTO> getUsers() {
        return userManagementService.findAll();
    }
}
