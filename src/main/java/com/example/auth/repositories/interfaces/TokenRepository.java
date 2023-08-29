package com.example.auth.repositories.interfaces;

import com.example.auth.models.AuthToken;
import com.example.auth.models.EncryptedUser;
import com.example.auth.models.User;

import java.util.UUID;

public interface TokenRepository {
    UUID GenerateToken(String username);

    void InvalidateToken(UUID tokenId);

    AuthToken GetAuthToken(UUID tokenId);

    boolean IsValid(UUID tokenId);
}
