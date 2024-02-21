package org.palette.easelauthservice.component.mailsender;

import lombok.RequiredArgsConstructor;
import org.palette.aop.InternalErrorLogging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailAuthMailSenderByJMS implements EmailAuthMailSender {
    private static final String SUBJECT = "Easel 이메일 인증";
    private static final String AUTH_MAIL_FORM = "이메일 인증번호 : ";

    @Value("${mail-host-address}")
    private String mailHostAddress;

    private final JavaMailSender javaMailSender;

    @InternalErrorLogging
    @Override
    public void send(
            String to,
            String authPayload
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailHostAddress);
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(AUTH_MAIL_FORM + authPayload);
        javaMailSender.send(message);
    }
}
