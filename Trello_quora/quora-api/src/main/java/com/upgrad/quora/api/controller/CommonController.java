package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.CommonService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CommonController {
    @Autowired
    private CommonService commonService;

    @RequestMapping(method = RequestMethod.GET, path="/userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUserDetails(@PathVariable("userId") final String userUuid, @RequestHeader("authorization") final String accessToken) throws UserNotFoundException, AuthorizationFailedException {
        final UserEntity userEntity = commonService.getUser(userUuid, accessToken);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().firstName(userEntity.getLastName())
                .lastName(userEntity.getLastName()).userName(userEntity.getUsername()).emailAddress(userEntity.getEmail())
                .contactNumber(userEntity.getContactnumber()).country(userEntity.getCountry()).aboutMe(userEntity.getAboutme())
                .dob(userEntity.getDob());
        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }
}
