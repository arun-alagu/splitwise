package com.example.splitwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.splitwise.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
}
