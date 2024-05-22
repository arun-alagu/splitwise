package com.example.splitwise.services;

import java.lang.reflect.Field;
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

    public User createUser(CreateUserRequest request) {

        User user = User.builder().email(request.getEmail()).name(request.getName())
                .phoneNumber(request.getPhoneNumber()).build();

        return userRepository.save(user);
    }

    public User updateUser(CreateUserRequest request, UUID id) {
        User user = userRepository.findById(id).orElseThrow();

        Field[] fields = request.getClass().getDeclaredFields();
        for(Field field : fields){
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
}
