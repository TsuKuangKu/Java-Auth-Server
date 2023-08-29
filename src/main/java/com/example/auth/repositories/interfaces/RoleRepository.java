package com.example.auth.repositories.interfaces;

import com.example.auth.models.Role;

public interface RoleRepository {
    boolean CreateRole(Role role);

    boolean DeleteRole(String name);

    Role GetRole(String name);
}
