package com.example.auth.repositories.interfaces;

import com.example.auth.models.EncryptedUser;
import com.example.auth.models.Role;
import com.example.auth.models.User;

public interface UserRepository {

    boolean CreateUser(User user);

    boolean DeleteUser(String username);

    EncryptedUser GetUesr(String username);

    boolean AssignRole(String username, Role role);

    boolean IsValid(User user);
}
