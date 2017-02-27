package de.belmega.biohazard.auth.common.dto;

import de.belmega.biohazard.auth.persistence.entities.ApplicationRole;
import de.belmega.biohazard.auth.persistence.entities.UserEntity;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private String mailAddress;
    private Set<ApplicationRole> roles = new HashSet<>();

    public UserDTO() {

    }

    public UserDTO(UserEntity userEntity) {
        this.mailAddress = userEntity.getMailAddress();
        this.roles = userEntity.getRoles();
    }

    public String getMailAddress() {
        return this.mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getName() {
        return getMailAddress().substring(0, getMailAddress().indexOf('@'));
    }

    public void addRole(ApplicationRole role) {
        this.roles.add(role);
    }

    public Set<ApplicationRole> getRoles() {
        return roles;
    }

}
