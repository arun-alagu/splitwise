package com.example.splitwise.repository;

import com.example.splitwise.models.RatioSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatioSplitRepository extends JpaRepository<RatioSplit, UUID> {
}
