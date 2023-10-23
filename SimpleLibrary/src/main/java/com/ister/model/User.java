package com.ister.model;


import com.ister.common.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity<String> {
    private String password;
    private String role;
}