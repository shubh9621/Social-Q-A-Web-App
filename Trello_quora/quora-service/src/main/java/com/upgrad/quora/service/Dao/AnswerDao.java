package com.upgrad.quora.service.Dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AnswerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public AnswerEntity createAnswer(AnswerEntity answerEntity){
        entityManager.persist(answerEntity);
        return answerEntity;
    }

    public AnswerEntity getAnswerByUuid(final String uuid){
        AnswerEntity answerEntity;
        try{
            answerEntity = entityManager.createNamedQuery("answerByUuid", AnswerEntity.class).setParameter("uuid", uuid).getSingleResult();
        }catch(NoResultException nre){
            answerEntity = null;
        }
        return answerEntity;
    }

    public void editAnswer(final AnswerEntity answerEntity){
        entityManager.merge(answerEntity);
    }

    public void deleteAnswer(final AnswerEntity answerEntity){
        entityManager.remove(answerEntity);
    }
}
