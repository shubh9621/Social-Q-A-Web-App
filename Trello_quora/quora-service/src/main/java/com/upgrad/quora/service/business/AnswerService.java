package com.upgrad.quora.service.business;

import com.upgrad.quora.service.Dao.AnswerDao;
import com.upgrad.quora.service.Dao.QuestionDao;
import com.upgrad.quora.service.Dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AnswerDao answerDao;
    @Autowired
    private QuestionDao questionDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity createAnswer(String accessToken, String questionId, AnswerEntity answerEntity) throws AuthorizationFailedException, InvalidQuestionException {
        final UserAuthTokenEntity userAuthTokenEntity = userDao.userByAccessToken(accessToken);
        if(userAuthTokenEntity == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        if(userAuthTokenEntity.getLogoutAt() == null || userAuthTokenEntity.getLoginAt().isAfter(userAuthTokenEntity.getLogoutAt())){
            QuestionEntity questionEntity = questionDao.getQuestionByUuid(questionId);
            if(questionEntity == null){
                throw new InvalidQuestionException("QUES-001","The question entered is invalid");
            }
            answerEntity.setQuestion(questionEntity);
            return answerDao.createAnswer(answerEntity);
        }else {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to post an answer");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity editAnswer(String accessToken, String answerId, AnswerEntity editedEntity) throws AuthorizationFailedException, AnswerNotFoundException {
        final UserAuthTokenEntity userAuthTokenEntity = userDao.userByAccessToken(accessToken);
        if(userAuthTokenEntity == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        if(userAuthTokenEntity.getLogoutAt() == null || userAuthTokenEntity.getLoginAt().isAfter(userAuthTokenEntity.getLogoutAt())){
            AnswerEntity answerEntity = answerDao.getAnswerByUuid(answerId);
            if(answerEntity == null){
                throw new AnswerNotFoundException("ANS-001","Entered answer uuid does not exist");
            }
            if(answerEntity.getUser().getUuid() == userAuthTokenEntity.getUser().getUuid()){
                answerDao.editAnswer(editedEntity);
            }else {
                throw new AuthorizationFailedException("ATHR-003","Only the answer owner can edit the answer");
            }
            return answerEntity;
        }else {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to post an answer");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity deleteAnswer(final String accessToken, final String uuid) throws AuthorizationFailedException, AnswerNotFoundException {
        final UserAuthTokenEntity userAuthTokenEntity = userDao.userByAccessToken(accessToken);
        if(userAuthTokenEntity == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        if(userAuthTokenEntity.getLogoutAt() == null || userAuthTokenEntity.getLoginAt().isAfter(userAuthTokenEntity.getLogoutAt())){
            AnswerEntity answerEntity = answerDao.getAnswerByUuid(uuid);
            if(answerEntity == null){
                throw new AnswerNotFoundException("ANS-001","Entered answer uuid does not exist");
            }
            if(answerEntity.getUser().getUuid() == userAuthTokenEntity.getUser().getUuid() || userAuthTokenEntity.getUser().getRole() == "admin"){
                answerDao.deleteAnswer(answerEntity);
            }else{
                throw new AuthorizationFailedException("ATHR-003","Only the answer owner or admin can delete the answer");
            }
            return answerEntity;
        }else {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.");
        }
    }
}
