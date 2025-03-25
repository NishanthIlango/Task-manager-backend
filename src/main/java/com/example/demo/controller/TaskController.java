package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/search")
    public List<Task> findTasksByName(@RequestParam String name) {
        return taskService.findTasksByName(name);
    }

    @PutMapping
    public Task createOrUpdateTask(@RequestBody Task task) {
        return taskService.createOrUpdateTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }

    @PostMapping("/{id}/execute")
    public Task executeTask(@PathVariable String id) {
        return taskService.executeTask(id);
    }
}
