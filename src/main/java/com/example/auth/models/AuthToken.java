package com.example.auth.models;

import java.time.ZonedDateTime;
import java.util.UUID;

public class AuthToken {

    private final UUID id;
    private final String username;
    private final ZonedDateTime expiryDate;

    public AuthToken(UUID id, String username) {
        this.id = id;
        this.username = username;
        this.expiryDate = ZonedDateTime.now().plusHours(2);
    }

    public UUID id() {
        return id;
    }

    public boolean IsExpired() {
        return expiryDate.isBefore(ZonedDateTime.now());
    }

    public String username() {
        return username;
    }
}
