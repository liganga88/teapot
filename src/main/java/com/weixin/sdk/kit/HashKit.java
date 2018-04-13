package com.weixin.sdk.kit;

import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * Created by Administrator on 2018/4/13.
 */
public class HashKit {
    private static SecureRandom random = new SecureRandom();

    public HashKit() {
    }

    public static String md5(String srcStr) {
        return hash("MD5", srcStr);
    }

    public static String sha1(String srcStr) {
        return hash("SHA-1", srcStr);
    }

    public static String sha256(String srcStr) {
        return hash("SHA-256", srcStr);
    }

    public static String sha384(String srcStr) {
        return hash("SHA-384", srcStr);
    }

    public static String sha512(String srcStr) {
        return hash("SHA-512", srcStr);
    }

    public static String hash(String algorithm, String srcStr) {
        try {
            StringBuilder e = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
            byte[] arr$ = bytes;
            int len$ = bytes.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                byte b = arr$[i$];
                String hex = Integer.toHexString(b & 255);
                if(hex.length() == 1) {
                    e.append("0");
                }

                e.append(hex);
            }

            return e.toString();
        } catch (Exception var10) {
            throw new RuntimeException(var10);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        byte[] arr$ = bytes;
        int len$ = bytes.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            String hex = Integer.toHexString(b & 255);
            if(hex.length() == 1) {
                result.append("0");
            }

            result.append(hex);
        }

        return result.toString();
    }

    public static String generateSalt(int numberOfBytes) {
        byte[] salt = new byte[numberOfBytes];
        random.nextBytes(salt);
        return toHex(salt);
    }
}
