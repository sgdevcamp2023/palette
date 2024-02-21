package org.palette.easelauthservice.component.mailsender;

public interface EmailAuthMailSender {

    void send(String to, String authPayload);
}
