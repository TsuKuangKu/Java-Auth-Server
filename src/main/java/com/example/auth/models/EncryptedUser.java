package com.example.auth.models;

import java.util.HashMap;

public class EncryptedUser {
    private final String username;
    private final byte[] password;

    private final HashMap<String, Role> roles;

    public EncryptedUser(String username, byte[] password) {
        this.username = username;
        this.password = password;
        this.roles = new HashMap<>();
    }

    public String username() {
        return username;
    }

    public byte[] password() {
        return password;
    }

    public HashMap<String, Role> roles() {
        return roles;
    }

    public void AddRole(Role role) {
        this.roles.put(role.name(), role);
    }

    public boolean HasRole(Role role) {
        return this.roles.containsKey(role.name());
    }
}
