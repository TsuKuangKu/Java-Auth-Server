package com.example.auth.repositories;

import com.example.auth.models.EncryptedUser;
import com.example.auth.models.Role;
import com.example.auth.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserRepositoryImplTest {

    @Test
    void createUser_whenUserNotExisted_shouldReturnTrue() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");

        boolean result = userRepository.CreateUser(mockUser);

        Assertions.assertTrue(result);
    }

    @Test
    void createUser_whenUserExisted_shouldReturnFalse() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");

        boolean createResult = userRepository.CreateUser(mockUser);
        boolean duplicatedResult = userRepository.CreateUser(mockUser);

        Assertions.assertTrue(createResult);
        Assertions.assertFalse(duplicatedResult);
    }

    @Test
    void deleteUser_whenUserExisted_shouldReturnTrue() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");

        boolean createResult = userRepository.CreateUser(mockUser);
        boolean deleteResult = userRepository.DeleteUser(mockUser.username());

        Assertions.assertTrue(createResult);
        Assertions.assertTrue(deleteResult);
    }

    @Test
    void deleteUser_whenUserNotExisted_shouldReturnFalse() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");

        boolean deleteResult = userRepository.DeleteUser(mockUser.username());

        Assertions.assertFalse(deleteResult);
    }

    @Test
    void getUser_whenUserExisted_shouldReturnUser() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");

        boolean result = userRepository.CreateUser(mockUser);
        EncryptedUser encryptedUser = userRepository.GetUser(mockUser.username());

        Assertions.assertTrue(result);
        Assertions.assertEquals(mockUser.username(),encryptedUser.username());
    }

    @Test
    void getUser_whenUserNotExisted_shouldReturnNull() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");

        EncryptedUser encryptedUser = userRepository.GetUser(mockUser.username());

        Assertions.assertNull(encryptedUser);
    }

    @Test
    void assignRole_whenRoleIsNull_shouldReturnFalse() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");

        boolean createResult = userRepository.CreateUser(mockUser);
        boolean assignResult = userRepository.AssignRole(mockUser.username(), null);

        Assertions.assertTrue(createResult);
        Assertions.assertFalse(assignResult);
    }

    @Test
    void assignRole_whenRoleIsNotNull_shouldReturnTrue() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");
        Role mockRole = new Role("mockRoleName");

        boolean createResult = userRepository.CreateUser(mockUser);
        boolean assignResult = userRepository.AssignRole(mockUser.username(), mockRole);

        Assertions.assertTrue(createResult);
        Assertions.assertTrue(assignResult);
    }

    @Test
    void isValid_whenUserNotExisted_shouldReturnFalse() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");

        boolean result = userRepository.IsValid(mockUser);

        Assertions.assertFalse(result);
    }

    @Test
    void isValid_whenPasswordIncorrect_shouldReturnFalse() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");
        User wrongUser = new User("mockUsername", "123456789");

        boolean createResult = userRepository.CreateUser(mockUser);
        boolean validateResult = userRepository.IsValid(wrongUser);

        Assertions.assertTrue(createResult);
        Assertions.assertFalse(validateResult);
    }

    @Test
    void isValid_whenPasswordCorrect_shouldReturnTrue() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User mockUser = new User("mockUsername", "12345678");
        User wrongUser = new User("mockUsername", "123456789");

        boolean createResult = userRepository.CreateUser(mockUser);
        boolean validateResult = userRepository.IsValid(mockUser);

        Assertions.assertTrue(createResult);
        Assertions.assertTrue(validateResult);
    }
}