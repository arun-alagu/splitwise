package com.example.splitwise.services;

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

        if (user.getEmail().equals(null) || !(request.getEmail() == null)
                && !request.getEmail().isEmpty()
                && !user.getEmail().equals(request.getEmail()))
            user.setEmail(request.getEmail());

        if (user.getName().equals(null) || !(request.getName() == null)
                && !request.getName().isEmpty()
                && !user.getName().equals(request.getName()))
            user.setName(request.getName());

        if (user.getPhoneNumber().equals(null) || !(request.getPhoneNumber() == null)
                && !user.getPhoneNumber().equals(request.getPhoneNumber()))
            user.setPhoneNumber(request.getPhoneNumber());

        return userRepository.save(user);
    }
}
