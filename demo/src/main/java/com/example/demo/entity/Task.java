package com.example.demo.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task description cannot be blank")
    private String task;

    private boolean completed;
    private boolean deleted;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date dateAdded;
    private Long userId;
    private String userName;

    public Task(String task, boolean completed, boolean deleted,Long userId,String username) {
        this.task = task;
        this.completed = completed;
        this.deleted = deleted;
        this.dateAdded = new Date(); // This will be overridden by @CreatedDate
        this.userId=userId;
        this.userName=username;
    }

    public Task() {
        // Default constructor
    }




}
