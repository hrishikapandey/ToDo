package com.example.restapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.entity.Task;
import com.example.restapi.repository.TaskRepository;

@RestController
@RequestMapping("/api/tasks")  // Note the plural 'tasks' here
public class TaskController {
    
    @Autowired
    private TaskRepository taskRepository;

    // Create a new task
    @PostMapping("/add")  // POST method to create a task
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // Save the task to the database
        Task savedTask = taskRepository.save(task);
        
        // Return a ResponseEntity with the saved task and a 201 CREATED status
        return ResponseEntity.status(201).body(savedTask);
    }

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get a specific task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        
        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        // Retrieve the task by ID, or throw an exception if not found
        Task task = taskRepository.findById(id)
                .orElseThrow();

        // Update the task with the new data from the request body
        task.setTaskName(taskDetails.getTaskName());
          // Assuming task has status

        // Save the updated task back to the database
        Task updatedTask = taskRepository.save(task);

        // Return the updated task with a 200 OK status
        return ResponseEntity.ok(updatedTask);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeTask(@PathVariable Long id) {
        // Check if task exists
        Optional<Task> task = taskRepository.findById(id);
        
        if (task.isPresent()) {
            // Delete the task
            taskRepository.deleteById(id);
            // Return success message
            return ResponseEntity.ok("Task with ID " + id + " deleted successfully");
        } else {
            // Return not found if the task does not exist
            return ResponseEntity.notFound().build();
        }
    }
}
