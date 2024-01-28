package org.palette.easelauthservice.component.generator.passport;

public record UserInfo(
        Long id,
        String email,
        String name,
        String gender,
        Integer age,
        String role
) {}
