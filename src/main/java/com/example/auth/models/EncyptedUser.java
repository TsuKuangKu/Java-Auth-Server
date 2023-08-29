package com.example.auth.models;

public class EncyptedUser {
    private final String username;

    private final byte[] password;

    public EncyptedUser(String username, byte[] password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPassword() {
        return password;
    }
}
