package com.example.auth.controllers.v1;

import com.example.auth.models.EncryptedUser;
import com.example.auth.models.Role;
import com.example.auth.models.User;
import com.example.auth.repositories.interfaces.RoleRepository;
import com.example.auth.repositories.interfaces.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleControllerTest {

    @Test
    void createRole_whenRoleNotExisted_shouldReturnsCREATEDResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        RoleController roleController = new RoleController(roleRepository, null);
        Role mockRole = new Role("mockRoleName");
        when(roleRepository.CreateRole(mockRole)).thenReturn(true);

        ResponseEntity<String> response = roleController.CreateRole(mockRole);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createRole_whenRoleExisted_shouldReturnsCONFLICTEDResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        RoleController roleController = new RoleController(roleRepository, null);
        Role mockRole = new Role("mockRoleName");
        when(roleRepository.CreateRole(mockRole)).thenReturn(false);

        ResponseEntity<String> response = roleController.CreateRole(mockRole);

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void deleteUser_whenRoleExisted_shouldReturnsOKResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        RoleController roleController = new RoleController(roleRepository, null);
        String mockRoleName = "mockRoleName";
        when(roleRepository.DeleteRole(mockRoleName)).thenReturn(true);

        ResponseEntity<String> response = roleController.DeleteUser(mockRoleName);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteUser_whenRoleNotExisted_shouldReturnsNOTFOUNDResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        RoleController roleController = new RoleController(roleRepository, null);
        String mockRoleName = "mockRoleName";
        when(roleRepository.DeleteRole(mockRoleName)).thenReturn(false);

        ResponseEntity<String> response = roleController.DeleteUser(mockRoleName);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void assignRoleToUser_whenRoleNotExisted_shouldReturnsNOTFOUNDResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        RoleController roleController = new RoleController(roleRepository, userRepository);
        String mockRoleName = "mockRoleName";
        User mockUser = new User("mockUserName", "12345678");
        when(roleRepository.GetRole(mockRoleName)).thenReturn(null);

        ResponseEntity<String> response = roleController.AssignRoleToUser(mockRoleName, mockUser);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void assignRoleToUser_whenRoleExistedUserNotExisted_shouldReturnsNOTFOUNDResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        RoleController roleController = new RoleController(roleRepository, userRepository);
        String mockRoleName = "mockRoleName";
        String mockUsername = "mockUserName";
        User mockUser = new User(mockUsername, "12345678");
        Role mockRole = new Role(mockRoleName);
        when(roleRepository.GetRole(mockRoleName)).thenReturn(mockRole);
        when(userRepository.GetUesr(mockUsername)).thenReturn(null);

        ResponseEntity<String> response = roleController.AssignRoleToUser(mockRoleName, mockUser);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void assignRoleToUser_whenRoleExistedUserExisted_shouldReturnsOKResponse() {
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        RoleController roleController = new RoleController(roleRepository, userRepository);
        String mockRoleName = "mockRoleName";
        String mockUsername = "mockUserName";
        User mockUser = new User(mockUsername, "12345678");
        Role mockRole = new Role(mockRoleName);
        EncryptedUser mockEncryptedUser = new EncryptedUser(mockUsername, null);
        when(roleRepository.GetRole(mockRoleName)).thenReturn(mockRole);
        when(userRepository.GetUesr(mockUsername)).thenReturn(mockEncryptedUser);

        ResponseEntity<String> response = roleController.AssignRoleToUser(mockRoleName, mockUser);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}