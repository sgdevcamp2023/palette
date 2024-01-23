package org.palette.easelsocialservice.common;

public interface PathEncoder {
    String encode(Long num);
    Long decode(String path);
}
