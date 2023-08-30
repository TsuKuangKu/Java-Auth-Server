package com.example.auth.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AESEncryptorTest {

    @Test
    void generateSecretKey_shouldReturnSecretKeySpec() {
        SecretKeySpec secretKeySpec = AESEncryptor.GenerateSecretKey();

        Assertions.assertNotNull(secretKeySpec);
        Assertions.assertEquals(SecretKeySpec.class, secretKeySpec.getClass());
    }

    @Test
    void encrypt_shouldReturnTheSameValue_whenContentsAreTheSame() {
        String testContent = "test content";
        String testContent2 = "test content2";

        SecretKeySpec secretKeySpec = AESEncryptor.GenerateSecretKey();

        byte[] encryptedContent1_1 = AESEncryptor.Encrypt(secretKeySpec, testContent);
        byte[] encryptedContent1_2 = AESEncryptor.Encrypt(secretKeySpec, testContent);
        byte[] encryptedContent2 = AESEncryptor.Encrypt(secretKeySpec, testContent2);

        assertArrayEquals(encryptedContent1_1, encryptedContent1_2);
        Assertions.assertFalse(Arrays.equals(encryptedContent1_1, encryptedContent2));
    }

    @Test
    void encrypt_shouldReturnDifferentValue_whenKeysAreTheDifferent() {
        String testContent = "test content";

        SecretKeySpec secretKeySpec1 = AESEncryptor.GenerateSecretKey();
        SecretKeySpec secretKeySpec2 = AESEncryptor.GenerateSecretKey();

        byte[] encryptedContent1 = AESEncryptor.Encrypt(secretKeySpec1, testContent);
        byte[] encryptedContent2 = AESEncryptor.Encrypt(secretKeySpec2, testContent);

        Assertions.assertFalse(Arrays.equals(encryptedContent1, encryptedContent2));
    }
}