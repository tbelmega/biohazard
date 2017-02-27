package de.belmega.biohazard.auth.service;

import de.belmega.biohazard.auth.common.dto.UserDTO;
import de.belmega.biohazard.auth.persistence.dao.UserDAO;
import de.belmega.biohazard.auth.persistence.entities.UserEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserManagementService {

    @Inject
    UserDAO userDAO;

    public List<UserDTO> findAll() {
        List<UserEntity> userEntityList = userDAO.findAll();

        List<UserDTO> userDTOList =
                userEntityList
                        .stream()
                        .map(userEntity -> createDTO(userEntity))
                        .collect(Collectors.toList());
        return userDTOList;
    }

    private UserDTO createDTO(UserEntity userEntity) {
        UserDTO dto = new UserDTO(userEntity.getMailAddress(), userEntity.getRoles());
        return dto;
    }
}
