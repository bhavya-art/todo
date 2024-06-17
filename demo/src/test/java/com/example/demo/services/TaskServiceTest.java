package com.example.demo.services;

import com.example.demo.models.Task;

import com.example.demo.repositories.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void shouldCreateTask(){
        Task task = new Task();
        task.setTask("junit test");
        task.setCompleted(false);
        Task tasknew = taskService.createNewTask(task);
        List<Task> tasks = taskService.getAllTask();
        taskService.findTaskById(tasknew.getId());
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTask()).isEqualTo("junit test");
        assertThat(tasks.get(0)).isEqualTo(tasknew);

    }
    @AfterEach
    public void tearDown(){
        taskRepository.deleteAll();
    }

}
