package com.example.auth.repositories;

import com.example.auth.models.EncyptedUser;
import com.example.auth.models.User;
import com.example.auth.repositories.interfaces.UserRepository;
import com.example.auth.utils.AESEncryptor;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;

@Service
public class UserRepositoryImpl implements UserRepository {
    private final HashMap<String, EncyptedUser> userTable;
    private final SecretKeySpec secretKey;

    public UserRepositoryImpl() {
        this.userTable = new HashMap<>();
        this.secretKey = AESEncryptor.GenerateSecretKey();
    }

    @Override
    public boolean CreateUser(User user) {
        // TODO: add username and password format validation
        String username = user.getUsername();
        if (this.userTable.containsKey(username)) {
            return false;
        }
        byte[] encryptedPassword = AESEncryptor.Encrypt(secretKey, user.getPassword());
        EncyptedUser encyptedUser = new EncyptedUser(username, encryptedPassword);
        this.userTable.put(username, encyptedUser);
        return true;
    }

    @Override
    public boolean DeleteUser(String username) {
        if (!this.userTable.containsKey(username)) {
            return false;
        }
        this.userTable.remove(username);
        return true;
    }
}
