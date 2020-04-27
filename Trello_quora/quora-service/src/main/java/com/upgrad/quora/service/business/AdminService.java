package com.upgrad.quora.service.business;

import com.upgrad.quora.service.Dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity deleteUser(final String accessToken, final String uuid) throws AuthorizationFailedException, UserNotFoundException {
        final UserAuthTokenEntity userAuthTokenEntity = userDao.userByAccessToken(accessToken);
        if(userAuthTokenEntity == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        if(userAuthTokenEntity.getLogoutAt() == null || userAuthTokenEntity.getLoginAt().isAfter(userAuthTokenEntity.getLogoutAt())){
            UserEntity userEntity = userDao.userByUuid(uuid);
            if(userEntity == null){
                throw new UserNotFoundException("USR-001","User with entered uuid to be deleted does not exist");
            }
            if(userEntity.getRole() == "nonadmin"){
                throw new AuthorizationFailedException("ATHR-003","Unauthorized Access, Entered user is not an admin");
            }
            userDao.deleteUser(userEntity);
            return userEntity;
        }else {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.");
        }
    }
}
