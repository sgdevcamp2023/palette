package org.palette.easelsocialservice.common;

public class Base62PathEncoder implements PathEncoder {

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = CHARACTERS.length();

    public String encode(Long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.insert(0, CHARACTERS.charAt((int) (num % BASE)));
            num /= BASE;
        }
        while (sb.length() < 7) {
            sb.insert(0, CHARACTERS.charAt(0));
        }
        return sb.toString();
    }

    public Long decode(String path) {
        long number = 0;
        for (int i = 0; i < path.length(); i++) {
            number = number * BASE + CHARACTERS.indexOf(path.charAt(i));
        }
        return number;
    }
}
