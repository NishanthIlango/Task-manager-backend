package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.TaskExecution;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public List<Task> findTasksByName(String name) {
        return taskRepository.findByNameContaining(name);
    }

    public Task createOrUpdateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    public Task executeTask(String id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            TaskExecution execution = new TaskExecution();
            execution.setStartTime(new Date());

            // Execute the shell command
            try {
                Process process = Runtime.getRuntime().exec(task.getCommand());
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                execution.setOutput(output.toString());
                execution.setEndTime(new Date());
            } catch (Exception e) {
                execution.setOutput("Execution failed: " + e.getMessage());
            }

            task.getTaskExecutions().add(execution);
            return taskRepository.save(task);
        }
        return null;
    }
}
