package pl.zaliczto.simpay.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class Hashes {
    private Hashes() {}

    public static String sha256Hex(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public static boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) return false;
        int result = a.length() ^ b.length();
        for (int i = 0; i < Math.min(a.length(), b.length()); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}

