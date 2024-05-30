package com.example.splitwise.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Split extends BaseModel{
    @ManyToOne
    private User user;
    private Float amount;
    private Float percentage;
    private Integer ratio;

}
