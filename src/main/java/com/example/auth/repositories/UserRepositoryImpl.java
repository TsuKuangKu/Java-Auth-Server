package com.example.auth.repositories;

import com.example.auth.models.EncryptedUser;
import com.example.auth.models.Role;
import com.example.auth.models.User;
import com.example.auth.repositories.interfaces.UserRepository;
import com.example.auth.utils.AESEncryptor;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class UserRepositoryImpl implements UserRepository {
    private final HashMap<String, EncryptedUser> userTable;
    private final SecretKeySpec secretKey;

    public UserRepositoryImpl() {
        this.userTable = new HashMap<>();
        this.secretKey = AESEncryptor.GenerateSecretKey();
    }

    @Override
    public boolean CreateUser(User user) {
        // TODO: add username and password format validation
        String username = user.username();
        if (this.userTable.containsKey(username)) {
            return false;
        }
        byte[] encryptedPassword = AESEncryptor.Encrypt(secretKey, user.password());
        EncryptedUser encyptedUser = new EncryptedUser(username, encryptedPassword);
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

    @Override
    public EncryptedUser GetUesr(String username) {
        return this.userTable.get(username);
    }

    @Override
    public boolean AssignRole(String username, Role role) {
        EncryptedUser user = this.userTable.get(username);
        user.AddRole(role);
        this.userTable.put(username, user);
        return true;
    }

    @Override
    public boolean IsValid(User user) {
        EncryptedUser encryptedUser = this.userTable.get(user.username());
        if (encryptedUser == null) {
            return false;
        }
        byte[] encryptedPassword = AESEncryptor.Encrypt(secretKey, user.password());
        return Arrays.equals(encryptedPassword, encryptedUser.password());
    }
}
