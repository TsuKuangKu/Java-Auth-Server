package com.example.auth.controllers.v1;

import com.example.auth.models.Role;
import com.example.auth.models.User;
import com.example.auth.models.EncryptedUser;
import com.example.auth.repositories.interfaces.RoleRepository;
import com.example.auth.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<String> CreateRole(@RequestBody Role role) {
        if (this.roleRepository.CreateRole(role)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("The role was successfully created.");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("The role is already existed!");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> DeleteUser(@PathVariable String name) {
        if (this.roleRepository.DeleteRole(name)) {
            return ResponseEntity.status(HttpStatus.OK).body("The role was successfully deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The role is not existed!");
    }

    @PostMapping("/{name}")
    public ResponseEntity<String> AssignRoleToUser(@PathVariable String name, @RequestBody User user) {
        Role role = this.roleRepository.GetRole(name);
        if (role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The role is not existed!");
        }
        EncryptedUser encryptedUser = this.userRepository.GetUser(user.username());
        if (encryptedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user is not existed!");
        }
        this.userRepository.AssignRole(encryptedUser.username(), role);
        return ResponseEntity.status(HttpStatus.OK).body("The role is successfully assigned.");
    }
}
