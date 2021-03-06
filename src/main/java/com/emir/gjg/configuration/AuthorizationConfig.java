package com.emir.gjg.configuration;

import com.emir.gjg.enums.Role;
import com.emir.gjg.exception.UnauthorizedException;
import com.emir.gjg.security.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

import static com.emir.gjg.constant.ErrorConstants.NOT_AUTHORIZED_FOR_OPERATION;

/**
 * Created by Emir Gökdemir
 * on 9 Mar 2020
 */
@Configuration
public class AuthorizationConfig {

    @Autowired
    private JwtResolver jwtResolver;

    public UUID permissionCheck(String token, Role minAuthRole) {
        Role userRole = jwtResolver.getRoleFromToken(token);
        if (!greaterCheck(userRole, minAuthRole)) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
        return jwtResolver.getIdFromToken(token);
    }

    private Boolean greaterCheck(Role userRole, Role minRole) {
        return userRole.toValue() >= minRole.toValue();
    }
}
