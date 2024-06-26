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

import java.util.Date;

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
        User user = userRepository.findByUsername(taskRequest.getUsername());
        if(user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found");
        }
        Task task = new Task();
        task.setTask(taskRequest.getTask());
        task.setDateAdded(new Date());
        task.setUserId(user.getUserId());
        task.setUserName(user.getUsername());
        taskRepository.save(task);

        return TaskResponse.builder().task(task.getTask()).build();
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.getById(id);
    }

    public List<Task> findAllCompletedTask() {
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> findAllInCompleteTask() {
        return taskRepository.findByCompletedFalse();
    }

    public void deleteTask(String username,Long id) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found"));

        if (!task.getUserId().equals(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to delete this task");
        }

        taskRepository.delete(task);

    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

}
