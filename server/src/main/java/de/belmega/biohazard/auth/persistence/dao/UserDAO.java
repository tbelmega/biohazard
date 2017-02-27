package de.belmega.biohazard.auth.persistence.dao;

import de.belmega.biohazard.auth.common.EncryptionUtil;
import de.belmega.biohazard.auth.common.dto.UserDTO;
import de.belmega.biohazard.auth.persistence.entities.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;

@Stateless
public class UserDAO {

    @PersistenceContext
    EntityManager em;

    public void saveUser(UserDTO user) {
        UserEntity userEntity;

        List<UserEntity> userEntities = findUserByMailAddress(user);
        if (userEntities.size() < 1) {
            userEntity = new UserEntity();
            userEntity.setSalt(EncryptionUtil.getNextSalt());
        } else {
            userEntity = userEntities.get(0);
        }

        userEntity.setMailAddress(user.getMailAddress());
        userEntity.setHashedPassword(user.getEncryptedPassword(userEntity.getSalt()));
        userEntity.setRoles(user.getRoles());

        em.persist(userEntity);
    }

    /**
     * Find the user entity with the given mail address.
     * If there is no such user, return empty list. If there is more than one user, throw NoUniqueResultException.
     *
     * @param user the user to search
     * @return a list with the matching user entity as only element. empty list, if no matching user.
     */
    public List<UserEntity> findUserByMailAddress(@NotNull UserDTO user) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = builder.createQuery(UserEntity.class);
        Root<UserEntity> from = criteria.from(UserEntity.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("mailAddress"), user.getMailAddress()));

        TypedQuery<UserEntity> typed = em.createQuery(criteria);
        List<UserEntity> resultList = typed.getResultList();

        if (resultList.size() > 1) throw new NonUniqueResultException();

        return resultList;
    }


    public List<UserEntity> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> entityRoot = query.from(UserEntity.class);

        query.select(entityRoot);

        return em.createQuery(query).getResultList();
    }
}
