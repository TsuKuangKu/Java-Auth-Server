package com.example.auth.repositories;

import com.example.auth.models.Role;
import com.example.auth.models.User;
import com.example.auth.repositories.interfaces.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleRepositoryImplTest {
    @Test
    void createRole_whenRoleNotExisted_shouldReturnTrue() {
        RoleRepositoryImpl roleRepository = new RoleRepositoryImpl();
        Role mockRole = new Role("mockRoleName");

        boolean result = roleRepository.CreateRole(mockRole);

        Assertions.assertTrue(result);
    }

    @Test
    void createRole_whenRoleExisted_shouldReturnFalse() {
        RoleRepositoryImpl roleRepository = new RoleRepositoryImpl();
        Role mockRole = new Role("mockRoleName");

        boolean createResult = roleRepository.CreateRole(mockRole);
        boolean duplicatedResult = roleRepository.CreateRole(mockRole);

        Assertions.assertTrue(createResult);
        Assertions.assertFalse(duplicatedResult);
    }

    @Test
    void deleteRole_whenRoleNotExisted_shouldReturnFalse() {
        RoleRepositoryImpl roleRepository = new RoleRepositoryImpl();

        boolean result = roleRepository.DeleteRole("mockRoleName");

        Assertions.assertFalse(result);
    }

    @Test
    void deleteRole_whenRoleExisted_shouldReturnTrue() {
        RoleRepositoryImpl roleRepository = new RoleRepositoryImpl();
        Role mockRole = new Role("mockRoleName");

        boolean createResult = roleRepository.CreateRole(mockRole);
        boolean deleteResult = roleRepository.DeleteRole(mockRole.name());

        Assertions.assertTrue(createResult);
        Assertions.assertTrue(deleteResult);
    }

    @Test
    void getRole_whenRoleNotExisted_shouldReturnNull() {
        RoleRepositoryImpl roleRepository = new RoleRepositoryImpl();

        Role role = roleRepository.GetRole("mockRoleName");

        Assertions.assertNull(role);
    }

    @Test
    void getRole_whenRoleExisted_shouldReturnRole() {
        RoleRepositoryImpl roleRepository = new RoleRepositoryImpl();
        Role mockRole = new Role("mockRoleName");

        boolean createResult = roleRepository.CreateRole(mockRole);
        Role roleResult = roleRepository.GetRole(mockRole.name());

        Assertions.assertTrue(createResult);
        Assertions.assertEquals(mockRole.name(), roleResult.name());
    }
}