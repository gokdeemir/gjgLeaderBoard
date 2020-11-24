package com.emir.gjg.controller;

import com.emir.gjg.configuration.AuthorizationConfig;
import com.emir.gjg.enums.Role;
import com.emir.gjg.model.resource.ScoreResource;
import com.emir.gjg.service.ScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 23 Ağu 2020
 */

@RestController
@RequestMapping("/score")
@Api(value = "Score", tags = {"Score Operations"})
public class ScoreController {

    @Autowired
    private ScoreService service;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);

    @ApiOperation(value = "Submit Score of an user", response = ScoreResource.class)
    @PostMapping("/submit")
    public ResponseEntity<ScoreResource> submitScore(@RequestHeader("Authorization") String token,
                                                     @RequestParam Double scoreWorth,
                                                     @RequestParam UUID userId) {
        logger.info("Requesting submitScore");
        authorizationConfig.permissionCheck(token, Role.MANAGER);
        return ResponseEntity.ok(service.submitScore(scoreWorth, userId));
    }
}

