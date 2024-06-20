package com.example.demo.controllers;

import com.example.demo.entity.Task;
import com.example.demo.models.TaskRequest;
import com.example.demo.models.TaskResponse;
import com.example.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTask());
    }
    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getAllCompletedTasks() {
        return ResponseEntity.ok(taskService.findAllCompletedTask());
    }
    @GetMapping("/incomplete")
    public ResponseEntity<List<Task>> getAllIncompleteTasks() {
        return ResponseEntity.ok(taskService.findAllInCompleteTask());
    }
    @PostMapping("/")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.createNewTask(taskRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task updatedTask) {
        Task existingTask = taskService.findTaskById(id);
        existingTask.setTask(updatedTask.getTask());
        existingTask.setCompleted(updatedTask.isCompleted());
        Task savedTask = taskService.updateTask(existingTask);
        return ResponseEntity.ok(savedTask);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> getAllTasks(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(true);
    }
}
