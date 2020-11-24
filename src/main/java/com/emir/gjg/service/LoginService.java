package com.emir.gjg.service;

import com.emir.gjg.constant.ErrorConstants;
import com.emir.gjg.exception.UnauthorizedException;
import com.emir.gjg.mapper.UserMapper;
import com.emir.gjg.model.dto.LoginDto;
import com.emir.gjg.model.entity.User;
import com.emir.gjg.model.resource.CompleteUserResource;
import com.emir.gjg.model.resource.LoginResource;
import com.emir.gjg.repository.UserRepository;
import com.emir.gjg.security.JwtGenerator;
import com.emir.gjg.security.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    public LoginResource login(LoginDto dto) {
        String emailOrUserName = dto.getEmailOrUsername();
        User user = (emailOrUserName.contains("@") ? userRepository.findByEmail(emailOrUserName) : userRepository.findUserByUserName(emailOrUserName));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user != null && encoder.matches(dto.getPassword(), user.getPassword())) {
            if (!user.isConfirmed()) {
                throw new UnauthorizedException(ErrorConstants.PLEASE_CONFIRM_YOUR_EMAIL_ADDRESS);
            }
            String token = jwtGenerator.generateLoginToken(user.getId(), user.getRole());
            CompleteUserResource userResource = userMapper.toResource(user);
            return new LoginResource(userResource, token);
        } else {
            throw new UnauthorizedException(ErrorConstants.WRONG_EMAIL_OR_PASSWORD);
        }
    }

    public LoginResource updateToken(String token) {
        UUID userId = jwtResolver.getIdFromToken(token);
        User user = userService.fromIdToEntity(userId);
        return new LoginResource(userMapper.toResource(user),jwtGenerator.generateLoginToken(userId, user.getRole()));
    }
}
