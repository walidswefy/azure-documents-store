package com.example.api.storage.security;

import lombok.AllArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author walid.sewaify
 * @since 14-Dec-20
 */
@AllArgsConstructor
public class AccessKeyService {
    private final String signingKey;

    public String secureHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest((input + signingKey).getBytes(UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder buffer = new StringBuilder();
        for (byte b : hash) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }
}
