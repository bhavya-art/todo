package com.example.demo.models;

import jakarta.persistence.*;
import java.util.Date;


@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // this is the primary key which will be auto generated
    private Long id;
    private String task;
    private boolean completed;
    private boolean deleted;
    @Column(nullable = false, updatable = false)

    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dateAdded;

    public Task(String task, boolean completed,boolean deleted) {
        this.task = task;
        this.completed = completed;
        this.deleted = deleted;
        this.dateAdded = new Date();
    }

    public Task() {

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public boolean isDeleted() {return deleted;};
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;}
    public Date getDateAdded() {
        return dateAdded;
    }
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
