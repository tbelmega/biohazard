package de.belmega.biohazard.auth.service;

import de.belmega.biohazard.auth.common.dto.UserDTO;
import de.belmega.biohazard.auth.persistence.dao.UserDAO;
import de.belmega.biohazard.auth.persistence.entities.UserEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AuthService {

    @Inject
    UserDAO userDAO;

    public boolean validate(UserDTO user) {
        List<UserEntity> userEntity = userDAO.findUserByMailAddress(user);
        if (userEntity.size() < 1)
            return false;
        else
            return validatePassword(user, userEntity.get(0));
    }

    private boolean validatePassword(UserDTO user, UserEntity userEntity) {
        String enteredEncryptedPassword = user.getEncryptedPassword();
        String storedEncryptedPassword = userEntity.getPassword();
        return enteredEncryptedPassword.equals(storedEncryptedPassword);
    }
}
