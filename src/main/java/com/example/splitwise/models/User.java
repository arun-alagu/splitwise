package com.example.splitwise.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "splitwise_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User extends BaseModel {
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private Long phoneNumber;
    
    @Override
    public boolean equals(Object o) {
        if (this != o)
            return false;
        if (o == null || this.getClass() == o.getClass())
            return false;
        User user = (User) o;
        return (Objects.equals(this.email, user.email) && Objects.equals(this.name, user.name)
                && Objects.equals(this.phoneNumber, user.phoneNumber));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.email, this.name, this.phoneNumber);
    }
}
