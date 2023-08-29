package com.example.auth.controllers.v1;

import com.example.auth.models.User;
import com.example.auth.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<String> CreateUser(@RequestBody User user) {
        if (this.userRepository.CreateUser(user)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("The user was successfully created.");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("The user is already existed!");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> DeleteUser(@PathVariable String username) {
        if (this.userRepository.DeleteUser(username)) {
            return ResponseEntity.status(HttpStatus.OK).body("The user was successfully deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user is not existed!");
    }
}
