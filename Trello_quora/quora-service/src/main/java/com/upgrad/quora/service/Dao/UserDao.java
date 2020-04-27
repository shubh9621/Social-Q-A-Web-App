package com.upgrad.quora.service.Dao;

import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity createUser(UserEntity userEntity){
        entityManager.persist(userEntity);
        return userEntity;
    }

    public UserEntity userByUsername(final String username){
        return entityManager.createNamedQuery("userByUsername", UserEntity.class).setParameter("username", username).getSingleResult();
    }

    public UserEntity userByEmail(final String email){
        return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email).getSingleResult();
    }

}
