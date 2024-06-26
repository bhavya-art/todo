package com.example.demo.services;

import com.example.demo.entity.Task;
import com.example.demo.models.TaskRequest;
import com.example.demo.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceUnitTest {

    @Mock
    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskService = new TaskService();
        taskService.setTaskRepository(taskRepository);
    }

    @Test
    public void shouldCreate(){
        TaskRequest taskrequest = new TaskRequest();
        Task task = new Task();
        when(taskRepository.save(task)).thenReturn(task);
        taskService.createNewTask(taskrequest);
    }
    @Test
    public void shouldGetAllTasks(){
        Task task1 = new Task();
        task1.setTask("Task1");
        Task task2 = new Task();
        task2.setTask("Task2");
        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks);
        taskService.getAllTask();
        verify(taskRepository, times(1)).findAll();
    }
    @Test
    public void shouldGetTaskById(){
        Task task = new Task();
        task.setTask("Task by id");
        Long taskId = 1L;
        when(taskRepository.getById(taskId)).thenReturn(task);
        taskService.findTaskById(taskId);
        verify(taskRepository, times(1)).getById(taskId);
    }
    @Test
    public void shouldUpdateTask(){
        Task task = new Task();
        task.setTask("Task updated");
        when(taskRepository.save(task)).thenReturn(task);
        taskService.updateTask(task);
        verify(taskRepository).save(task);
    }
    @Test
//    public void shouldDeleteTask(){
//        Task task = new Task();
//        task.setTask("Task deleted");
//        Long taskId = 1L;
//        taskService.deleteTask(taskId);
//        verify(taskRepository, times(1)).deleteById(taskId);
//    }
    @Test
    public void shouldFindAllCompletedTasks(){
        Task task = new Task();
        task.setTask("completed task");
        task.setCompleted(true);
        when(taskRepository.findByCompletedTrue()).thenReturn(Arrays.asList(task));
        taskService.findAllCompletedTask();
        verify(taskRepository, times(1)).findByCompletedTrue();
    }
    @Test
    public void shouldFindIncompleteTasks(){
        Task task = new Task();
        task.setTask("incomplete task");
        when(taskRepository.findByCompletedFalse()).thenReturn(Arrays.asList(task));
        taskService.findAllInCompleteTask();
        verify(taskRepository, times(1)).findByCompletedFalse();
    }

}
