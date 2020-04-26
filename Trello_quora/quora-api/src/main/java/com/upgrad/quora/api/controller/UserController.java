package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.api.model.SignupUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    @RequestMapping("/user/signup")
    public ResponseEntity<SignupUserResponse> signup(final SignupUserRequest signupUserRequest){
        return new ResponseEntity<SignupUserResponse>(HttpStatus.OK);
    }
}
