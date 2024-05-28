package com.example.splitwise.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.splitwise.models.Group;

public interface GroupRepository extends JpaRepository<Group, UUID>{

}
