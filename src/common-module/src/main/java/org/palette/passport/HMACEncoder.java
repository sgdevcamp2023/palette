package org.palette.passport;

import lombok.RequiredArgsConstructor;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@RequiredArgsConstructor
public class HMACEncoder {

    private final String HMacAlgorithm;
    private final String passportSecretKey;

    protected String createHMACIntegrityKey(String userInfoString) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                passportSecretKey.getBytes(),
                HMacAlgorithm
        );
        Mac mac;
        try {
            mac = Mac.getInstance(HMacAlgorithm);
            mac.init(secretKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder()
                .encodeToString(mac.doFinal(userInfoString.getBytes()));
    }
}
