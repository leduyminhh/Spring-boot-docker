package com.fpt.egov.apigateway.configs.security.csrf;

import java.security.SecureRandom;
import java.util.Base64;
public class SecureCRFSToken {

    private static SecureCRFSToken instances;
    private final SecureRandom secureRandom;
    private final Base64.Encoder base64Encoder;
    private final int length;

    public SecureCRFSToken() {
        this.length = 50;
        this.secureRandom = new SecureRandom();
        this.base64Encoder = Base64.getUrlEncoder();
    }

    public static SecureCRFSToken getInstances() {
        if (instances == null) {
            instances = new SecureCRFSToken();
        }

        return instances;
    }

    public String generateNewToken() {
        byte[] randomBytes = new byte[this.length];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public String generateNewToken(int length) {
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
