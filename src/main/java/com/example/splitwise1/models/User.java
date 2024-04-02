package com.example.splitwise1.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String name;
    private String password;
    private String email;

    public boolean equals(User user) {
        return this.getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
