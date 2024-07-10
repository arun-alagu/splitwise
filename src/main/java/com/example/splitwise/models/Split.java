package com.example.splitwise.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Split extends BaseModel{
    @ManyToOne
    private User user;
    private Float amount;
}
