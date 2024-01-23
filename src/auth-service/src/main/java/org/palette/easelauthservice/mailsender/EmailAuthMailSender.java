package org.palette.easelauthservice.mailsender;

public interface EmailAuthMailSender {

    void send(String to, String from, String authPayload);
}
