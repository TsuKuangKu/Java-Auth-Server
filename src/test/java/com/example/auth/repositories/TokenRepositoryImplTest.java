package com.example.auth.repositories;

import com.example.auth.models.AuthToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class TokenRepositoryImplTest {

    @Test
    void generateToken_whenUsernameIsNotEmpty_shouldReturnUUID() {
        TokenRepositoryImpl tokenRepository = new TokenRepositoryImpl();
        String mockUsername = "mockUsername";

        UUID tokenId = tokenRepository.GenerateToken(mockUsername);

        Assertions.assertEquals(UUID.class, tokenId.getClass());
    }

    @Test
    void generateToken_whenUsernameIsEmpty_shouldThrowException() {
        TokenRepositoryImpl tokenRepository = new TokenRepositoryImpl();
        String emptyUsername = "";

        Assertions.assertThrows(IllegalArgumentException.class, () -> tokenRepository.GenerateToken(emptyUsername));
    }

    @Test
    void getAuthToken_whenTokenExisted_shouldReturnAuthToken() {
        TokenRepositoryImpl tokenRepository = new TokenRepositoryImpl();
        String mockUsername = "mockUsername";

        UUID tokenId = tokenRepository.GenerateToken(mockUsername);
        AuthToken token = tokenRepository.GetAuthToken(tokenId);

        Assertions.assertEquals(UUID.class, tokenId.getClass());
        Assertions.assertEquals(tokenId, token.id());
    }

    @Test
    void getAuthToken_whenTokenNotExisted_shouldReturnNull() {
        TokenRepositoryImpl tokenRepository = new TokenRepositoryImpl();
        UUID fakeTokenId = UUID.randomUUID();

        AuthToken token = tokenRepository.GetAuthToken(fakeTokenId);

        Assertions.assertNull(token);
    }

    @Test
    void isValid_whenTokenExisted_shouldReturnTrue() {
        TokenRepositoryImpl tokenRepository = new TokenRepositoryImpl();
        String mockUsername = "mockUsername";

        UUID tokenId = tokenRepository.GenerateToken(mockUsername);
        boolean result = tokenRepository.IsValid(tokenId);

        Assertions.assertTrue(result);
    }

    @Test
    void isValid_whenTokenNoExisted_shouldReturnFalse() {
        TokenRepositoryImpl tokenRepository = new TokenRepositoryImpl();
        UUID fakeTokenId = UUID.randomUUID();

        boolean result = tokenRepository.IsValid(fakeTokenId);

        Assertions.assertFalse(result);
    }

    @Test
    void invalidateToken_whenTokenExisted_shouldRemoveToken() {
        TokenRepositoryImpl tokenRepository = new TokenRepositoryImpl();
        String mockUsername = "mockUsername";

        UUID tokenId = tokenRepository.GenerateToken(mockUsername);
        AuthToken token = tokenRepository.GetAuthToken(tokenId);
        tokenRepository.InvalidateToken(tokenId);
        AuthToken invalidatedToken = tokenRepository.GetAuthToken(tokenId);

        Assertions.assertEquals(UUID.class, tokenId.getClass());
        Assertions.assertEquals(tokenId, token.id());
        Assertions.assertNull(invalidatedToken);
    }
}