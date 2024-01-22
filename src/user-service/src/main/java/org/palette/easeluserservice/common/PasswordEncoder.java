package org.palette.easeluserservice.common;

public interface PasswordEncoder {

    String encode(String inputPassword);

    Boolean match(String inputPassword, String persistencePassword);
}
