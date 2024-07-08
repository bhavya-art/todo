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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/tasks")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtService jwtService;

    private TaskResponse convertToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTask(task.getTask());
        response.setCompleted(task.isCompleted());
        response.setDateAdded(task.getDateAdded());
        response.setUsername(task.getUser().getUsername());
        return response;
    }

    // Get all tasks for the authenticated user
    @GetMapping("/")
    public ResponseEntity<List<TaskResponse>> getAllTasks(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to return all tasks for user: {}", userDetails.getUsername());
        List<TaskResponse> taskResponses = taskService.getAllTasksByUsername(userDetails.getUsername())
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskResponses);
    }

    // Get all completed tasks for the authenticated user
    @GetMapping("/completed")
    public ResponseEntity<List<TaskResponse>> getAllCompletedTasks(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to return all completed tasks for user: {}", userDetails.getUsername());
        List<TaskResponse> taskResponses = taskService.findAllCompletedTaskByUsername(userDetails.getUsername())
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskResponses);
    }

    // Get all incomplete tasks for the authenticated user
    @GetMapping("/incomplete")
    public ResponseEntity<List<TaskResponse>> getAllIncompleteTasks(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to return all incomplete tasks for user: {}", userDetails.getUsername());
        List<TaskResponse> taskResponses = taskService.findAllIncompleteTaskByUsername(userDetails.getUsername())
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskResponses);
    }

    // Create a new task for the authenticated user
    @PostMapping("/")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        log.info("Request to create a task for user: {}", username);
        taskRequest.setUsername(username);
        TaskResponse taskResponse = taskService.createNewTask(taskRequest);
        return ResponseEntity.ok(taskResponse);
    }

    // Update a task for the authenticated user
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable("id") Long id, @RequestBody Task updatedTask, @AuthenticationPrincipal UserDetails userDetails) {
        Task savedTask;
        try {
            Task existingTask = taskService.findTaskById(id);
            log.info("Found existing task: {}", existingTask);

            // Check if the task belongs to the authenticated user
            if (!existingTask.getUser().getUsername().equals(userDetails.getUsername())) {
                return ResponseEntity.status(403).build();
            }

            existingTask.setTask(updatedTask.getTask());
            existingTask.setCompleted(updatedTask.isCompleted());
            savedTask = taskService.updateTask(existingTask);
        } catch (Exception e) {
            log.error("Something failed", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(convertToResponse(savedTask));
    }

    // Delete a task for the authenticated user
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to delete task");

        Task task = taskService.findTaskById(id);
        if (task == null || !task.getUser().getUsername().equals(userDetails.getUsername())) {
            return ResponseEntity.status(403).build();
        }

        taskService.deleteTask(id);
        return ResponseEntity.ok(true);
    }
}
