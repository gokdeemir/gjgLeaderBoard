package com.emir.gjg.service;

import com.emir.gjg.constant.ErrorConstants;
import com.emir.gjg.exception.BadRequestException;
import com.emir.gjg.model.entity.User;
import com.emir.gjg.repository.UserRepository;
import com.emir.gjg.security.JwtGenerator;
import com.emir.gjg.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.emir.gjg.constant.MailConstants.*;


/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Service
public class TokenService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailUtil mailUtil;

    public void sendActivationTokenToMail(User user) {
        String confirmationToken = jwtGenerator.generateMailConfirmationToken(user.getId());
        mailUtil.sendTokenMail(user.getEmail(), confirmationToken, CONFIRM_ACCOUNT_HEADER,
                CONFIRM_ACCOUNT_BODY
                        + CLIENT_URL
                        + CONFIRM_ACCOUNT_URL);
    }

    public void sendResetPasswordTokenToMail(String email) {
        if (email == null) {
            throw new BadRequestException(ErrorConstants.PROVIDE_VALID_MAIL);
        }
        User user = userRepository.findByEmail(email);
        String resetToken = jwtGenerator.generateResetPasswordToken(user.getId());
        mailUtil.sendTokenMail(user.getEmail(), resetToken, RESET_PASSWORD_HEADER,
                RESET_PASSWORD_BODY + "\n"
                        + CLIENT_URL
                        + RESET_PASSWORD_URL);

    }
}

