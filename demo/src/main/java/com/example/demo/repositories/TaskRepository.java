package com.example.demo.repositories;

import com.example.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUsername(String username);
    List<Task> findByUsernameAndCompletedTrue(String username);
    List<Task> findByUsernameAndCompletedFalse(String username);
    Optional<Task> findByIdAndUsername(Long id, String username);

}
