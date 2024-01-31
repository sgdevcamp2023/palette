package org.palette.aop;

import org.palette.passport.component.UserInfo;

public class EaselAuthenticationContext {
    static final ThreadLocal<UserInfo> CONTEXT = new ThreadLocal<>();

    public static UserInfo getUserInfo() {
        return CONTEXT.get();
    }
}
