package com.upgrad.quora.service.Dao;

import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        UserEntity userEntity;
        try{
            userEntity = entityManager.createNamedQuery("userByUsername", UserEntity.class).setParameter("username", username).getSingleResult();
        }catch(NoResultException nre){
            userEntity = null;
        }
        return userEntity;
    }

    public UserEntity userByEmail(final String email){
        UserEntity userEntity;
        try{
            userEntity = entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email).getSingleResult();
        }catch(NoResultException nre){
            userEntity = null;
        }
        return userEntity;
    }

}
