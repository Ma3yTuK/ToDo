package com.todo.todo_back.entities;

import java.time.ZonedDateTime;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Task {

    public enum Fields {
        ID("id"),
        USER("user"),
        TITLE("title"),
        DESCRIPTION("description"),
        DUE("due"),
        STATUS("status");

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
    @Column(name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Title must be specified!")
    @Size(min = 2, message = "Title must be at least 2 characters long!")
    private String title;

    @Basic
    @Column(length = 4096)
    private String description;

    @NotNull(message = "Due must be specified!")
    private ZonedDateTime due;

    @NotNull(message = "Status must be specified!")
    private Boolean status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDue(ZonedDateTime due) {
        this.due = due;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getDue() {
        return due;
    }

    public Boolean getStatus() {
        return status;
    }
}
