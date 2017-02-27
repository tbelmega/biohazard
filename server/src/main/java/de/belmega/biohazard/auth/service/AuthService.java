package de.belmega.biohazard.auth.service;

import de.belmega.biohazard.auth.common.dto.UserCredentialDTO;
import de.belmega.biohazard.auth.common.dto.UserDTO;
import de.belmega.biohazard.auth.persistence.dao.UserDAO;
import de.belmega.biohazard.auth.persistence.entities.UserEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AuthService {

    @Inject
    UserDAO userDAO;

    public Optional<UserDTO> validate(UserCredentialDTO user) {
        Optional<UserEntity> userEntity = userDAO.findUserByMailAddress(user.getMailAddress());
        if (userEntity.isPresent())
            return validatePassword(user, userEntity.get());
        else
            return Optional.empty();
    }

    private Optional<UserDTO> validatePassword(UserCredentialDTO user, UserEntity userEntity) {
        byte[] enteredEncryptedPassword = user.getEncryptedPassword(userEntity.getSalt());
        byte[] storedEncryptedPassword = userEntity.getHashedPassword();

        if (enteredEncryptedPassword.length != storedEncryptedPassword.length) return Optional.empty();
        for (int i = 0; i < storedEncryptedPassword.length; i++)
            if (enteredEncryptedPassword[i] != storedEncryptedPassword[i]) return Optional.empty();

        return Optional.of(new UserDTO(userEntity));
    }
}
