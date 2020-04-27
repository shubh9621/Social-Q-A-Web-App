package com.upgrad.quora.service.Dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
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

    public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthTokenEntity){
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

    public UserAuthTokenEntity userByAccessToken(final String accessToken){
        UserAuthTokenEntity userAuthTokenEntity;
        try{
            userAuthTokenEntity = entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        }catch(NoResultException nre){
            userAuthTokenEntity = null;
        }
        return userAuthTokenEntity;
    }

    public void updateUserAuth(final UserAuthTokenEntity updatedUserAuthToken){
        entityManager.merge(updatedUserAuthToken);
    }

    public UserEntity userByUuid(final String userUuid){
        UserEntity userEntity;
        try{
            userEntity = entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", userUuid).getSingleResult();
        }catch(NoResultException nre){
            userEntity = null;
        }
        return userEntity;
    }

}
