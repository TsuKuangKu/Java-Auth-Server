package com.example.auth.controllers.v1;

import com.example.auth.models.User;
import com.example.auth.repositories.interfaces.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Test
    void createUser_whenUserNotExisted_shouldReturnsCREATEDResponse() {
        UserRepository userRepository = mock(UserRepository.class);
        UserController userController = new UserController(userRepository);
        User mockUser = new User("mockUserName", "12345678");
        when(userRepository.CreateUser(mockUser)).thenReturn(true);

        ResponseEntity<String> response = userController.CreateUser(mockUser);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUser_whenUserExisted_shouldReturnsCONFLICTResponse() {
        UserRepository userRepository = mock(UserRepository.class);
        UserController userController = new UserController(userRepository);
        User mockUser = new User("mockUserName", "12345678");
        when(userRepository.CreateUser(mockUser)).thenReturn(false);

        ResponseEntity<String> response = userController.CreateUser(mockUser);

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void deleteUser_whenUserExisted_shouldReturnsOKResponse() {
        UserRepository userRepository = mock(UserRepository.class);
        UserController userController = new UserController(userRepository);
        String mockUsername = "mockUserName";
        when(userRepository.DeleteUser(mockUsername)).thenReturn(true);

        ResponseEntity<String> response = userController.DeleteUser(mockUsername);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteUser_whenUserNotExisted_shouldReturnsNOTFOUNDResponse() {
        UserRepository userRepository = mock(UserRepository.class);
        UserController userController = new UserController(userRepository);
        String mockUsername = "mockUserName";
        when(userRepository.DeleteUser(mockUsername)).thenReturn(false);

        ResponseEntity<String> response = userController.DeleteUser(mockUsername);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}