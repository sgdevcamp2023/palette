package org.palette.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.palette.passport.PassportExtractor;
import org.palette.passport.component.Passport;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PassportAspect {

    private final HttpServletRequest httpServletRequest;
    private final PassportExtractor passportExtractor;

    @Around("@annotation(org.palette.aop.InjectEaselAuthentication)")
    public Object setUserInfoByServlet(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Passport passport = passportExtractor.getPassportFromRequestHeader(httpServletRequest);
        EaselAuthenticationContext.CONTEXT.set(passport);

        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }
}
