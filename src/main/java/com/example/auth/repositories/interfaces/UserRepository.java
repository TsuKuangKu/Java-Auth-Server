package com.example.auth.repositories.interfaces;

import com.example.auth.models.EncyptedUser;
import com.example.auth.models.User;

public interface UserRepository {

    boolean CreateUser(User user);

    boolean DeleteUser(String username);
}
