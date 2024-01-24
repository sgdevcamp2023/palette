package org.palette.easelauthservice.component.mailsender;

import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.config.MailSenderConfig;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailAuthMailSenderByJMS implements EmailAuthMailSender {
    private static final String SUBJECT = "Easel 이메일 인증";
    private static final String AUTH_MAIL_FORM = "이메일 인증번호 : ";

    private final JavaMailSender javaMailSender;

    @Override
    public void send(
            String to,
            String authPayload
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("palette1203@naver.com");
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(AUTH_MAIL_FORM + authPayload);
        javaMailSender.send(message);
    }
}
