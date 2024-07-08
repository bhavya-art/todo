package com.example.demo.services;

import com.example.demo.entity.Task;
import com.example.demo.models.TaskRequest;
import com.example.demo.models.TaskResponse;
import com.example.demo.entity.User;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createNewTask(TaskRequest taskRequest) {
        User user = userRepository.findByUsername(taskRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        Task task = new Task();
        task.setTask(taskRequest.getTask());
        task.equals(taskRequest.getUsername());
        task.setUser(user); // Setting the user for the task
        task.setUsername(user.getUsername());

        task = taskRepository.save(task);

        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTask(task.getTask());
        response.setCompleted(task.isCompleted());
        response.setDateAdded(task.getDateAdded());
        response.setUsername(user.getUsername());

        return response;
    }

    public List<Task> getAllTasksByUsername(String username) {
        return taskRepository.findByUsername(username);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found"));
    }

    public List<Task> findAllCompletedTaskByUsername(String username) {
        return taskRepository.findByUsernameAndCompletedTrue(username);
    }

    public List<Task> findAllIncompleteTaskByUsername(String username) {
        return taskRepository.findByUsernameAndCompletedFalse(username);
    }

    public void deleteTask(Long id) {
        Task task = findTaskById(id);
        taskRepository.delete(task);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }
}
