package com.emir.gjg.controller;

import com.emir.gjg.configuration.AuthorizationConfig;
import com.emir.gjg.model.dto.RegisterDto;
import com.emir.gjg.model.resource.LoginResource;
import com.emir.gjg.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.emir.gjg.constant.HttpSuccessMessagesConstants.YOUR_MAIL_WAS_CONFIRMED;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@RestController
@RequestMapping(value = {"/register"})
@Api(value = "Registration", tags = {"Registration"})
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @ApiOperation(value = "Register a user with the needed information", response = LoginResource.class)
    @PostMapping("/user")
    public ResponseEntity<LoginResource> registerUser(@RequestBody @Valid RegisterDto registerDto) {
        return ResponseEntity.ok(registerService.register(registerDto));
    }

    @ApiOperation(value = "Confirm a registration by using the link from the user's confirmation mail", response = String.class)
    @GetMapping("/confirm-register")
    public ResponseEntity<String> confirmRegister(@RequestHeader("Authorization") String confirmationToken) {
        registerService.confirmRegister(confirmationToken);
        return ResponseEntity.ok(YOUR_MAIL_WAS_CONFIRMED);
    }

}
