package com.example.auth.controllers.v1;

import com.example.auth.models.AuthToken;
import com.example.auth.models.EncryptedUser;
import com.example.auth.models.Role;
import com.example.auth.models.User;
import com.example.auth.repositories.interfaces.RoleRepository;
import com.example.auth.repositories.interfaces.TokenRepository;
import com.example.auth.repositories.interfaces.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Test
    void authenticate_whenUserInvalid_shouldReturnUNAUTHORIZEDResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        AuthController authController = new AuthController(roleRepository, userRepository, tokenRepository);
        User mockUser = new User("mockUsername", "12345678");
        when(userRepository.IsValid(mockUser)).thenReturn(false);

        ResponseEntity<String> response = authController.Authenticate(mockUser);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void authenticate_whenUserValid_shouldReturnOKResponseWithTokenId() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        AuthController authController = new AuthController(roleRepository, userRepository, tokenRepository);
        User mockUser = new User("mockUsername", "12345678");
        UUID mockUUID = UUID.randomUUID();
        when(userRepository.IsValid(mockUser)).thenReturn(true);
        when(tokenRepository.GenerateToken(mockUser.username())).thenReturn(mockUUID);

        ResponseEntity<String> response = authController.Authenticate(mockUser);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(mockUUID.toString(), response.getBody());
    }

    @Test
    void invalidateToken_whenTokenInvalid_shouldReturnUNAUTHORIZEDResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        AuthController authController = new AuthController(roleRepository, userRepository, tokenRepository);
        AuthToken mockToken = new AuthToken(UUID.randomUUID(), "mockUsername");
        when(tokenRepository.IsValid(mockToken.id())).thenReturn(false);

        ResponseEntity<String> response = authController.InvalidateToken(mockToken);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void invalidateToken_whenTokenValid_shouldReturnOKResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        AuthController authController = new AuthController(roleRepository, userRepository, tokenRepository);
        AuthToken mockToken = new AuthToken(UUID.randomUUID(), "mockUsername");
        when(tokenRepository.IsValid(mockToken.id())).thenReturn(true);

        ResponseEntity<String> response = authController.InvalidateToken(mockToken);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void isRoleAssigned_whenTokenInvalid_shouldReturnUNAUTHORIZEDResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        AuthController authController = new AuthController(roleRepository, userRepository, tokenRepository);
        AuthToken mockToken = new AuthToken(UUID.randomUUID(), "mockUsername");
        when(tokenRepository.IsValid(mockToken.id())).thenReturn(false);

        ResponseEntity<Boolean> response = authController.IsRoleAssigned("mockRole", mockToken);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void isRoleAssigned_whenTokenValidRoleNotExisted_shouldReturnOKResponseWithFalse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        AuthController authController = new AuthController(roleRepository, userRepository, tokenRepository);
        AuthToken mockToken = new AuthToken(UUID.randomUUID(), "mockUsername");
        String mockRoleName = "mockRole";
        when(tokenRepository.IsValid(mockToken.id())).thenReturn(true);
        when(tokenRepository.GetAuthToken(mockToken.id())).thenReturn(mockToken);
        when(roleRepository.GetRole(mockRoleName)).thenReturn(null);

        ResponseEntity<Boolean> response = authController.IsRoleAssigned(mockRoleName, mockToken);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(false, response.getBody());
    }

    @Test
    void isRoleAssigned_whenTokenValidRoleExisted_shouldReturnOKResponseWithTrue() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        AuthController authController = new AuthController(roleRepository, userRepository, tokenRepository);
        String mockUsername = "mockUsername";
        AuthToken mockToken = new AuthToken(UUID.randomUUID(), mockUsername);
        String mockRoleName = "mockRole";
        Role mockRole = new Role(mockRoleName);
        EncryptedUser mockEncryptedUser = new EncryptedUser(mockUsername, null);
        mockEncryptedUser.AddRole(mockRole);
        when(tokenRepository.IsValid(mockToken.id())).thenReturn(true);
        when(tokenRepository.GetAuthToken(mockToken.id())).thenReturn(mockToken);
        when(userRepository.GetUser(mockUsername)).thenReturn(mockEncryptedUser);
        when(roleRepository.GetRole(mockRoleName)).thenReturn(mockRole);

        ResponseEntity<Boolean> response = authController.IsRoleAssigned(mockRoleName, mockToken);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(true, response.getBody());
    }

    @Test
    void getRoles_whenTokenInvalid_shouldReturnUNAUTHORIZEDResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        AuthController authController = new AuthController(roleRepository, userRepository, tokenRepository);
        AuthToken mockToken = new AuthToken(UUID.randomUUID(), "mockUsername");
        when(tokenRepository.IsValid(mockToken.id())).thenReturn(false);

        ResponseEntity<HashMap<String,Role>> response = authController.GetRoles(mockToken);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void getRoles_whenTokenValid_shouldReturnOKResponseWithRoles() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        AuthController authController = new AuthController(roleRepository, userRepository, tokenRepository);
        String mockUsername = "mockUsername";
        AuthToken mockToken = new AuthToken(UUID.randomUUID(), mockUsername);
        String mockRoleName = "mockRole";
        Role mockRole = new Role(mockRoleName);
        EncryptedUser mockEncryptedUser = new EncryptedUser(mockUsername, null);
        mockEncryptedUser.AddRole(mockRole);
        when(userRepository.GetUser(mockUsername)).thenReturn(mockEncryptedUser);
        when(tokenRepository.IsValid(mockToken.id())).thenReturn(true);
        when(tokenRepository.GetAuthToken(mockToken.id())).thenReturn(mockToken);

        ResponseEntity<HashMap<String,Role>> response = authController.GetRoles(mockToken);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(mockEncryptedUser.roles(), response.getBody());
    }
}