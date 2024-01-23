package org.palette.easelauthservice.component.mailsender;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static org.palette.easelauthservice.component.mailsender.MailConst.*;

@Component
@RequiredArgsConstructor
public class EmailAuthMailSenderByJMS implements EmailAuthMailSender {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(
            String to,
            String authPayload
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(HOST);
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(AUTH_MAIL_FORM + authPayload);
        javaMailSender.send(message);
    }
}
