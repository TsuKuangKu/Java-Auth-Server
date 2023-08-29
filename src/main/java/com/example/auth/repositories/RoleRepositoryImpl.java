package com.example.auth.repositories;

import com.example.auth.models.Role;
import com.example.auth.repositories.interfaces.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RoleRepositoryImpl implements RoleRepository {
    private final HashMap<String, Role> roleTable;

    public RoleRepositoryImpl() {
        this.roleTable = new HashMap<>();
    }
    @Override
    public boolean CreateRole(Role role) {
        String name = role.name();
        if (this.roleTable.containsKey(name)) {
            return false;
        }
        this.roleTable.put(name, role);
        return true;
    }

    @Override
    public boolean DeleteRole(String name) {
        if (!this.roleTable.containsKey(name)) {
            return false;
        }
        this.roleTable.remove(name);
        return true;
    }

    @Override
    public Role GetRole(String name) {
        return this.roleTable.get(name);
    }
}
