package de.belmega.biohazard.auth.persistence.dao;

import de.belmega.biohazard.auth.common.EncryptionUtil;
import de.belmega.biohazard.auth.common.dto.UserCredentialDTO;
import de.belmega.biohazard.auth.common.dto.UserDTO;
import de.belmega.biohazard.auth.persistence.entities.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserDAO {

    @PersistenceContext
    EntityManager em;

    public void saveUser(UserDTO user) {
        UserEntity userEntity = findUserByMailAddress(user.getMailAddress()).orElse(createNewUserEntity(user));

        byte[] encryptedRandomPw = EncryptionUtil.generateRandomPassword(userEntity.getSalt());
        userEntity.setHashedPassword(encryptedRandomPw);
        userEntity.setRoles(user.getRoles());

        em.persist(userEntity);
    }

    /**
     * Create new user entity with given email address and a random salt.
     */
    private UserEntity createNewUserEntity(UserDTO user) {
        UserEntity userEntity;
        userEntity = new UserEntity();
        userEntity.setSalt(EncryptionUtil.getNextSalt());
        userEntity.setMailAddress(user.getMailAddress());
        return userEntity;
    }


    /**
     * Find the user entity with the given mail address.
     * If there is no such user, return empty list. If there is more than one user, throw NoUniqueResultException.
     *
     * @param mailaddress of the user to search
     * @return a list with the matching user entity as only element. empty list, if no matching user.
     */
    public Optional<UserEntity> findUserByMailAddress(@NotNull String mailaddress) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = builder.createQuery(UserEntity.class);
        Root<UserEntity> from = criteria.from(UserEntity.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("mailAddress"), mailaddress));

        TypedQuery<UserEntity> typed = em.createQuery(criteria);
        Optional<UserEntity> userEntity = typed.getResultList().stream().findFirst();
        return userEntity;
    }


    public List<UserEntity> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> entityRoot = query.from(UserEntity.class);

        query.select(entityRoot);

        return em.createQuery(query).getResultList();
    }

    public void updatePassword(UserCredentialDTO credentials) {
        UserEntity userEntity = findUserByMailAddress(credentials.getMailAddress()).get();
        userEntity.setHashedPassword(credentials.getEncryptedPassword(userEntity.getSalt()));
        em.persist(userEntity);
    }
}
