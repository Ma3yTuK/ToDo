package com.todo.todo_back.entities;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "t_user")
public class User implements UserDetails {

    public enum Fields {
        ID("id"),
        USERNAME("username"),
        PASSWORD("password");

        Fields(String databaseFieldName) {
            this.databaseFieldName = databaseFieldName;
        }

        private final String databaseFieldName;

        public String getDatabaseFieldName() {
            return databaseFieldName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @NotNull(message = "Username must be specified!")
    @Size(min = 2, message = "Username must be at least 2 characters long!")
    private String username;

    @NotNull(message = "Password must be specified!")
    @Size(min = 2, message = "Password must be at least 2 characters long!")
    private String password;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() { // Not supposed to be used
        return new HashSet<>();
    }
}

