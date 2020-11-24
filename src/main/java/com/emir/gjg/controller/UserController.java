package com.emir.gjg.controller;

import com.emir.gjg.common.AbstractController;
import com.emir.gjg.common.AbstractService;
import com.emir.gjg.configuration.AuthorizationConfig;
import com.emir.gjg.enums.Role;
import com.emir.gjg.model.dto.RegisterDto;
import com.emir.gjg.model.entity.User;
import com.emir.gjg.model.resource.CompleteUserResource;
import com.emir.gjg.model.resource.MinimalUserResource;
import com.emir.gjg.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 23 Ağu 2020
 */

@RestController
@RequestMapping("/user")
@Api(value = "User", tags = {"User Operations"})
public class UserController extends AbstractController<User, UUID, RegisterDto, MinimalUserResource> {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    @Override
    protected AbstractService<User, UUID, RegisterDto, MinimalUserResource> getService() {
        return userService;
    }

    @Override
    public void setSaveRole() {
        super.saveRole = Role.MANAGER;
    }

    @Override
    public void setGetRole() {
        super.getRole = Role.BASIC_USER;
    }

    @Override
    public void setGetAllRole() {
        super.getAllRole = Role.BASIC_USER;
    }

    @Override
    public void setUpdateRole() {
        super.updateRole = Role.MANAGER;
    }

    @Override
    public void setDeleteRole() {
        super.deleteRole = Role.MANAGER;
    }

    @Override
    public void setEntityName() {
        entityName = "User";
    }


    @ApiOperation(value = "Create Object, it can be done without authorization, this endpoint is created for demo.")
    @PostMapping("/create")
    public CompleteUserResource create(@RequestBody @Valid RegisterDto dto) {
        logger.info(String.format("Creating the user Dto with id: %s.", dto.getUserName()));
        return userService.create(dto);
    }
}

