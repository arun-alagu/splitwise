package com.example.splitwise.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.splitwise.models.Split;

@Repository
public interface SplitRepository extends JpaRepository< Split, UUID>{

}
