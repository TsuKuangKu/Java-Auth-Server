package com.example.auth.repositories;

import com.example.auth.models.AuthToken;
import com.example.auth.models.EncryptedUser;
import com.example.auth.repositories.interfaces.TokenRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.UUID;

@Service
public class TokenRepositoryImpl implements TokenRepository {

    // TODO: use in-memory cache with ttl to replace the HashMap
    private final HashMap<UUID, AuthToken> tokenTable;

    public TokenRepositoryImpl() {
        this.tokenTable = new HashMap<>();
    }

    @Override
    public UUID GenerateToken(String username) {
        if (username.isEmpty()) {
            throw new IllegalArgumentException();
        }
        UUID tokenId = UUID.randomUUID();
        AuthToken token = new AuthToken(tokenId, username);
        this.tokenTable.put(tokenId, token);
        return tokenId;
    }

    @Override
    public void InvalidateToken(UUID tokenId) {
        this.tokenTable.remove(tokenId);
    }

    @Override
    public AuthToken GetAuthToken(UUID tokenId) {
        return this.tokenTable.get(tokenId);
    }

    @Override
    public boolean IsValid(UUID tokenId) {
        AuthToken token = this.tokenTable.get(tokenId);
        if (token == null) {
            return false;
        }
        if (token.IsExpired()) {
            this.tokenTable.remove(tokenId);
            return false;
        }
        return true;
    }
}
