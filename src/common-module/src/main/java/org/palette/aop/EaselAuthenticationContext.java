package org.palette.aop;

import org.palette.passport.component.UserInfo;

public class EaselAuthenticationContext {
    static final ThreadLocal<UserInfo> CONTEXT = new ThreadLocal<>();

    public UserInfo getUser() {
        return CONTEXT.get();
    }
}
