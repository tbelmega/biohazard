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
        byte[] enteredEncryptedPassword = user.getEncryptedPassword(userEntity.getSalt());
        byte[] storedEncryptedPassword = userEntity.getHashedPassword();

        if (enteredEncryptedPassword.length != storedEncryptedPassword.length) return false;
        for (int i = 0; i < storedEncryptedPassword.length; i++)
            if (enteredEncryptedPassword[i] != storedEncryptedPassword[i]) return false;

        return true;
    }
}
