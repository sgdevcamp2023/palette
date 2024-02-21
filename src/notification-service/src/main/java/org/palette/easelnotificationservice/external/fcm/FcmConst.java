package org.palette.easelnotificationservice.external.fcm;

import org.springframework.beans.factory.annotation.Value;

public class FcmConst {

    @Value("${fcm.key}")
    protected String secretKey;

    @Value("${fcm.project-id}")
    protected String projectId;

    protected final String CONFIG_PATH = secretKey;
    protected final String AUTH_URL = "https://www.googleapis.com/auth/cloud-platform";
    protected final String SEND_URL = "https://fcm.googleapis.com/v1/projects/" + projectId + "/messages:send";
    protected static final String FCM_TOPIC = "Easel";
    public static final String PAINT_CREATED_TITLE = "Easel";
    public static final String PAINT_CREATED_BODY = "새로운 페인트가 게시되었습니다.";
}
