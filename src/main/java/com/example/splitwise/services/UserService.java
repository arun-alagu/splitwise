package com.example.splitwise.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.splitwise.dtos.CreateUserRequest;
import com.example.splitwise.models.User;
import com.example.splitwise.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET Mapping
    // GET User by Id
    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    // GET All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET Users list by Id list
    public List<User> getUsers(List<UUID> userIds) {
        List<User> users = new ArrayList<>();

        for (UUID userId : userIds) {
            users.add(getUser(userId));
        }

        return users;
    }

    // POST Mapping
    public User createUser(CreateUserRequest request) {

        User user = User.builder().email(request.getEmail()).name(request.getName())
                .phoneNumber(request.getPhoneNumber()).build();

        return userRepository.save(user);
    }

    // PATCH Mapping
    public User updateUser(CreateUserRequest request, UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        Field[] fields = request.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(request);
                if (value != null) {
                    Field userField = User.class.getDeclaredField(field.getName());
                    userField.setAccessible(true);
                    userField.set(user, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error updating user field: " + field.getName(), e);
            }

        }

        return userRepository.save(user);
    }

    // DELETE Mapping
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}
