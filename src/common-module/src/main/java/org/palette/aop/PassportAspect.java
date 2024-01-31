package org.palette.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
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
    public Object setUserInfoByServlet(ProceedingJoinPoint proceedingJoinPoint) {
        final Passport passport = passportExtractor.getPassportFromRequestHeader(httpServletRequest);
        EaselAuthenticationContext.CONTEXT.set(passport);

        try {
            return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        } catch (Throwable e) {
            throw new BaseException(ExceptionType.COMMON_500_000003);
        }
    }
}
