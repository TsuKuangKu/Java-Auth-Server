package com.example.auth.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AESEncryptor {
    private static final String algorithm = "AES";

    public static SecretKeySpec GenerateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encoded = secretKey.getEncoded();
            return new SecretKeySpec(encoded, algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate secret key");
        }
    }
    public static byte[] Encrypt(SecretKeySpec key, String content) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt content");
        }
    }

    // TODO: remove the method.
    public static String Decrypt(SecretKeySpec key, byte[] encryptedContent) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encryptedContent));
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt content");
        }
    }
}
