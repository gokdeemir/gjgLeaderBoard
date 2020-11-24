package com.emir.gjg.controller;

import com.emir.gjg.model.dto.LoginDto;
import com.emir.gjg.model.resource.LoginResource;
import com.emir.gjg.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Api(value = "Login", tags = {"Login"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @ApiOperation(value = "Login with the username (email) and password, " +
            "default test users: manager, admin, basicUser; with password 'GJG2020.' ", response = LoginResource.class)
    @PostMapping("")
    public ResponseEntity<LoginResource> login(@RequestBody LoginDto loginDto) {
        logger.info(String.format("Requesting login user's mail: %s ", loginDto.getEmailOrUsername()));
        return ResponseEntity.ok(loginService.login(loginDto));
    }


    @ApiOperation(value = "Update token of user by using old non-expired token", response = LoginResource.class)
    @GetMapping("/update-token")
    public ResponseEntity<LoginResource> tokenUpdate(@RequestHeader("Authorization") String token) {
        logger.info("Requesting tokenUpdate");
        return ResponseEntity.ok(loginService.updateToken(token));
    }
}
