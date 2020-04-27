package com.upgrad.quora.service.business;

import com.upgrad.quora.service.Dao.QuestionDoa;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class QuestionService {
    @Autowired
    private QuestionDoa questionDoa;

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity postQuestion(QuestionEntity questionEntity) throws AuthorizationFailedException {
        return questionDoa.createQuestion(questionEntity);
    }
}