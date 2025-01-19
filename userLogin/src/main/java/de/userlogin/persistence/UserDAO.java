package de.userlogin.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import static de.userlogin.persistence.UserEntity.COLUMN_MAIL_ADRESS;

import java.util.List;

@Stateless
public class UserDAO {

    @PersistenceContext
    EntityManager em;


    public void persist(UserEntity userEntity) {
        em.persist(userEntity);
    }

    public UserEntity findUserByMailadresse(String enteredEmailAdress) {

        // SELECT * FROM user WHERE mailAdress = enteredEmailAdress;

        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteria.createQuery(UserEntity.class);

        Root<UserEntity> fromTableUser = query.from(UserEntity.class);
        Path<Object> columnMailAdress = fromTableUser.get(COLUMN_MAIL_ADRESS);

        query.select(fromTableUser);
        query.where(criteria.equal(columnMailAdress, enteredEmailAdress));

        TypedQuery<UserEntity> typedQuery = em.createQuery(query);

        // Execute query
        List<UserEntity> resultList = typedQuery.getResultList();

        if (resultList.isEmpty()) return null;
        else return resultList.get(0);
    }

    public List<UserEntity> findAll() {
        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteria.createQuery(UserEntity.class);

        Root<UserEntity> fromTableUser = query.from(UserEntity.class);

        query.select(fromTableUser);

        TypedQuery<UserEntity> typedQuery = em.createQuery(query);

        // Execute query
        return typedQuery.getResultList();
    }
}
