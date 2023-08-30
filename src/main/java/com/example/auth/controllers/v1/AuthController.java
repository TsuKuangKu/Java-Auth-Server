package com.example.auth.controllers.v1;

import com.example.auth.models.AuthToken;
import com.example.auth.models.EncryptedUser;
import com.example.auth.models.Role;
import com.example.auth.models.User;
import com.example.auth.repositories.interfaces.RoleRepository;
import com.example.auth.repositories.interfaces.TokenRepository;
import com.example.auth.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    @Autowired
    public AuthController(RoleRepository roleRepository, UserRepository userRepository, TokenRepository tokenRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping()
    public ResponseEntity<String> Authenticate(@RequestBody User user) {
        if (!this.userRepository.IsValid(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The user is invalid!");
        }
        UUID tokenId = this.tokenRepository.GenerateToken(user.username());
        return ResponseEntity.status(HttpStatus.OK).body(tokenId.toString());
    }

    @DeleteMapping ()
    public ResponseEntity<String> InvalidateToken(@RequestBody AuthToken token) {
        if (!this.tokenRepository.IsValid(token.id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The token is invalid!");
        }

        this.tokenRepository.InvalidateToken(token.id());
        return ResponseEntity.status(HttpStatus.OK).body("The token is invalidated.");
    }

    @GetMapping ("/roles/{roleName}")
    public ResponseEntity<Boolean> IsRoleAssigned(@PathVariable String roleName, @RequestBody AuthToken token) {
        if (!this.tokenRepository.IsValid(token.id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        token = this.tokenRepository.GetAuthToken(token.id());
        Role role = this.roleRepository.GetRole(roleName);
        EncryptedUser encryptedUser = this.userRepository.GetUser(token.username());
        if (role == null || encryptedUser == null || !encryptedUser.HasRole(role)) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @GetMapping ("/roles")
    public ResponseEntity<HashMap<String, Role>> GetRoles(@RequestBody AuthToken token) {
        if (!this.tokenRepository.IsValid(token.id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        token = this.tokenRepository.GetAuthToken(token.id());
        EncryptedUser encryptedUser = this.userRepository.GetUser(token.username());
        return ResponseEntity.status(HttpStatus.OK).body(encryptedUser.roles());
    }
}
