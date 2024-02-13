package org.palette.aop;

import org.palette.passport.component.Passport;
import org.palette.passport.component.UserInfo;

public class EaselAuthenticationContext {
    static final ThreadLocal<Passport> CONTEXT = new ThreadLocal<>();

    public static UserInfo getUserInfo() {
        return CONTEXT.get().userInfo();
    }

    public static String getIntegrityKey() {
        return CONTEXT.get().integrityKey();
    }
}
