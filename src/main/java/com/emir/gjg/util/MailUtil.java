package com.emir.gjg.util;

import com.emir.gjg.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.emir.gjg.constant.ErrorConstants.MAIL_SEND_FAILED;

@Component
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTokenMail(String email, String activationToken, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setText(String.format(text, activationToken), true);
            mimeMessageHelper.setTo(email);
            mimeMessage.setSubject(subject);
        } catch (MessagingException messagingException) {
            throw new BadRequestException(MAIL_SEND_FAILED);
        }
        mailSender.send(mimeMessage);
    }

}
