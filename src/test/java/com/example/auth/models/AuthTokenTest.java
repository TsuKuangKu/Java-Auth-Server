package com.example.auth.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenTest {

    @Test
    void isExpired_whenExpiryDatePassed_shouldReturnTrue() {
        AuthToken authToken = new AuthToken(UUID.randomUUID(), "mockUserName");
        authToken.setExpiryDate(ZonedDateTime.now());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean result = authToken.IsExpired();

        Assertions.assertTrue(result);
    }

    @Test
    void isExpired_whenExpiryDateNotPassed_shouldReturnFalse() {
        AuthToken authToken = new AuthToken(UUID.randomUUID(), "mockUserName");
        authToken.setExpiryDate(ZonedDateTime.now().plusMinutes(1));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean result = authToken.IsExpired();

        Assertions.assertFalse(result);
    }
}