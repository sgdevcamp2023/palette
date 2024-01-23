package org.palette.easelauthservice.mailsender;

import org.springframework.beans.factory.annotation.Value;

public class MailConst {

    @Value("${spring.mail.host}")
    public static String HOST;

    @Value("${spring.mail.username}")
    public static String USERNAME;

    @Value("${spring.mail.password}")
    public static String PASSWORD;

    @Value("${spring.mail.port}")
    public static int PORT;

    public static final String SUBJECT = "Easel 이메일 인증";

    protected static final String AUTH_MAIL_FORM = "이메일 인증번호 : ";
}
