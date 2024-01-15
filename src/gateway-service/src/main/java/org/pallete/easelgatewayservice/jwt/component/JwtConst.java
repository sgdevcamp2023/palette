package org.pallete.easelgatewayservice.jwt.component;

class JwtConst {

    protected static final int BEARER_PREFIX_LENGTH = 7;
    protected static final Long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;
    protected static final Long REFRESH_TOKEN_EXPIRE_TIME = 270 * 24 * 60 * 60 * 1000L;
    protected static final String HEAD_PARAMETER_NAME = "type";
    protected static final String HEAD_PARAMETER_VALUE = "JWT";
    protected static final String CLAIMS_SUBJECT = "jwt";
    protected static final String CLAIMS_FIRST_FILED = "id";
}
