package org.palette.easeluserservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EurekaServiceName {
    SOCIAL_SERVICE_NAME("SOCIAL-SERVICE"),
    AUTH_SERVICE_NAME("AUTH-SERVICE"),
    ;

    final String value;
}
