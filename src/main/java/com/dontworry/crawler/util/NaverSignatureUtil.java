package com.dontworry.crawler.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class NaverSignatureUtil {

    public static String generate(String method, String uri, String timestamp, String secretKey) {
        try {
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8); // Base64 decode 금지
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA256");

            String message = timestamp + "." + method.toUpperCase() + "." + uri;

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(rawHmac);

        } catch (Exception e) {
            throw new RuntimeException("Signature 생성 실패", e);
        }
    }
}