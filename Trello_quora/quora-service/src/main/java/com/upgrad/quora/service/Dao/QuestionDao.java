package com.upgrad.quora.service.Dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionDao {
    @PersistenceContext
    private EntityManager entityManager;

    public QuestionEntity createQuestion (QuestionEntity questionEntity){
        entityManager.persist(questionEntity);
        return questionEntity;
    }

    public QuestionEntity questionByUuid(final String uuid) {
        QuestionEntity questionEntity;
        try {
            questionEntity = entityManager.createNamedQuery("questionByUuid" , QuestionEntity.class).setParameter("uuid" , uuid).getSingleResult();
        }
        catch(NoResultException nre) {
            questionEntity = null;
        }
    }

    public QuestionEntity questionByUserId(final int user_id) {
        QuestionEntity questionEntity;
        try {
            questionEntity = entityManager.createNamedQuery("questionByUserId" , QuestionEntity.class).setParameter("id" , user_id).getSingleResult();
        }
        catch(NoResultException nre) {
            questionEntity = null;
        }
    }

    public QuestionEntity questionByUserId(final int user_id) {
        QuestionEntity questionEntity;
        try {
            questionEntity = entityManager.createNamedQuery("questionByUserId" , QuestionEntity.class).setParameter("user_id" , user_id).getSingleResult();
        }
        catch(NoResultException nre) {
            questionEntity = null;
        }
    }

    public QuestionEntity questionByAnswerId(final int id) {
        QuestionEntity questionEntity;
        try {
            questionEntity = entityManager.createNamedQuery("questionByAnswerId" , QuestionEntity.class).setParameter("id" , id).getSingleResult();
        }
        catch(NoResultException nre) {
            questionEntity = null;
        }
    }

}