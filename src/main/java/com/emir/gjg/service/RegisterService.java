package com.emir.gjg.service;

import com.emir.gjg.exception.BadRequestException;
import com.emir.gjg.mapper.UserMapper;
import com.emir.gjg.model.dto.RegisterDto;
import com.emir.gjg.model.entity.User;
import com.emir.gjg.model.resource.LoginResource;
import com.emir.gjg.repository.UserRepository;
import com.emir.gjg.security.JwtGenerator;
import com.emir.gjg.security.JwtResolver;
import com.emir.gjg.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.emir.gjg.constant.ErrorConstants.MAIL_ALREADY_EXISTS;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Transactional
    public LoginResource register(RegisterDto registerDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = mapper.toEntity(registerDto);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException(MAIL_ALREADY_EXISTS);
        }
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setConfirmed(true);
        userRepository.saveAndFlush(user);
        tokenService.sendActivationTokenToMail(user);
        return new LoginResource(mapper.toResource(user), jwtGenerator.generateLoginToken(user.getId(), user.getRole()));
    }


    @Transactional
    public void confirmRegister(String confirmationToken) {
        UUID id = jwtResolver.getIdFromToken(confirmationToken);
        User user = userService.fromIdToEntity(id);
        user.setConfirmed(true);
        userRepository.save(user);
    }
}
