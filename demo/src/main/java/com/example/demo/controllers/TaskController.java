package com.example.demo.controllers;

import com.example.demo.entity.Task;
import com.example.demo.models.TaskRequest;
import com.example.demo.models.TaskResponse;
import com.example.demo.services.JwtService;
import com.example.demo.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to return all tasks for user: {}", userDetails.getUsername());
        return ResponseEntity.ok(taskService.getAllTasksByUsername(userDetails.getUsername()));
    }
    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getAllCompletedTasks() {
        log.info("Request to return all completed tasks");
        return ResponseEntity.ok(taskService.findAllCompletedTask());
    }
    @GetMapping("/incomplete")
    public ResponseEntity<List<Task>> getAllIncompleteTasks() {
        log.info("Request to return all incomplete tasks");
        return ResponseEntity.ok(taskService.findAllInCompleteTask());
    }
    @PostMapping("/")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Request to create a task for user: {}", username);
        taskRequest.setUsername(username);  // Set the username in TaskRequest
        return ResponseEntity.ok(taskService.createNewTask(taskRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task updatedTask) {
        Task savedTask;
        try {
            Task existingTask = taskService.findTaskById(id);
            log.info("Found existing task: {}", existingTask);
            existingTask.setTask(updatedTask.getTask());
            existingTask.setCompleted(updatedTask.isCompleted());
            savedTask = taskService.updateTask(existingTask);
        }catch (Exception e){
            log.error("Something  failed ", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(savedTask);
    }


    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<Boolean> getAllTasks(@PathVariable("username")String username,@PathVariable("id") Long id) {
        log.info("Request to delete task");
        taskService.deleteTask(username,id);
        return ResponseEntity.ok(true);
    }
}
