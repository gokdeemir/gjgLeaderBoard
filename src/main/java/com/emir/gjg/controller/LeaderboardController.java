package com.emir.gjg.controller;

import com.emir.gjg.configuration.AuthorizationConfig;
import com.emir.gjg.enums.Role;
import com.emir.gjg.model.resource.LeaderboardResource;
import com.emir.gjg.model.resource.LoginResource;
import com.emir.gjg.service.LeaderboardService;
import com.emir.gjg.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Emir Gökdemir
 * on 23 Ağu 2020
 */

@RestController
@RequestMapping("/leaderboard")
@Api(value = "Leaderboard", tags = {"Leaderboard Operations"})
public class LeaderboardController {

    @Autowired
    private LeaderboardService service;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    private static final Logger logger = LoggerFactory.getLogger(LeaderboardController.class);

    @ApiOperation(value = "Get leaderboard, it will return country based leaderboard, by default 0th page comes", response = LoginResource.class, responseContainer = "List")
    @GetMapping(value = {"/{country_iso_code}/{pageNo}","/{country_iso_code}"})
    public ResponseEntity<Page<LeaderboardResource>> getLeaderBoard(@RequestHeader("Authorization") String token,
                                                                    @PathVariable(name = "country_iso_code") String countryIso,
                                                                    @PathVariable(required = false) Integer pageNo) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        logger.info("Requesting getLeaderBoard");
        return ResponseEntity.ok(service.getLeaderBoard(countryIso,pageNo));
    }

    @ApiOperation(value = "Get leaderboard, it will return global leaderboard, by default 0th page comes", response = LoginResource.class, responseContainer = "List")
    @GetMapping(value = {"/{pageNo}","/"})
    public ResponseEntity<Page<LeaderboardResource>> getLeaderBoard(@RequestHeader("Authorization") String token,
                                                                    @PathVariable(required = false) Integer pageNo) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        logger.info("Requesting getLeaderBoard");
        return ResponseEntity.ok(service.getLeaderBoard(null,pageNo));
    }
}

