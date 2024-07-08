package com.example.demo.controllers;

import com.example.demo.entity.Task;
import com.example.demo.models.TaskRequest;
import com.example.demo.models.TaskResponse;
import com.example.demo.services.JwtService;
import com.example.demo.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/tasks")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtService jwtService;

    // Get all tasks for the authenticated user
    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to return all tasks for user: {}", userDetails.getUsername());
        return ResponseEntity.ok(taskService.getAllTasksByUsername(userDetails.getUsername()));
    }

    // Get all completed tasks for the authenticated user
    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getAllCompletedTasks(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to return all completed tasks for user: {}", userDetails.getUsername());
        return ResponseEntity.ok(taskService.findAllCompletedTaskByUsername(userDetails.getUsername()));
    }

    // Get all incomplete tasks for the authenticated user
    @GetMapping("/incomplete")
    public ResponseEntity<List<Task>> getAllIncompleteTasks(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to return all incomplete tasks for user: {}", userDetails.getUsername());
        return ResponseEntity.ok(taskService.findAllIncompleteTaskByUsername(userDetails.getUsername()));
    }

    // Create a new task for the authenticated user
    @PostMapping("/")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        log.info("Request to create a task for user: {}", username);
        taskRequest.setUsername(username);
        return ResponseEntity.ok(taskService.createNewTask(taskRequest));
    }

    // Update a task for the authenticated user
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task updatedTask, @AuthenticationPrincipal UserDetails userDetails) {
        Task savedTask;
        try {
            Task existingTask = taskService.findTaskById(id);
            log.info("Found existing task: {}", existingTask);

            // Check if the task belongs to the authenticated user
            if (!existingTask.getUser().getEmail().equals(userDetails.getUsername())) {
                return ResponseEntity.status(403).build();
            }

            existingTask.setTask(updatedTask.getTask());
            existingTask.setCompleted(updatedTask.isCompleted());
            savedTask = taskService.updateTask(existingTask);
        } catch (Exception e) {
            log.error("Something failed", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(savedTask);
    }

    // Delete a task for the authenticated user
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to delete task");

        Task task = taskService.findTaskById(id);
        if (task == null || !task.getUser().getEmail().equals(userDetails.getUsername())) {
            return ResponseEntity.status(403).build();
        }

        taskService.deleteTask(id);
        return ResponseEntity.ok(true);
    }
}
