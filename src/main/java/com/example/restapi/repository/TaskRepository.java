package com.example.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restapi.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
