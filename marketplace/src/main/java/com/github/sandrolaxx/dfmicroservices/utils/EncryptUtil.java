package com.github.sandrolaxx.dfmicroservices.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {

    public static String textEncrypt(final String plainText, final String key) {
        
        try {

            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, aesKey);

            byte[] encrypted = cipher.doFinal(plainText.getBytes());

            return java.util.Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String textDecrypt(final String cipherText, final String key) {
        
        try {

            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, aesKey);

            byte[] decordedValue = java.util.Base64.getDecoder().decode(cipherText);
            byte[] decValue = cipher.doFinal(decordedValue);

            return new String(decValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
