package com.todo.todo_back.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.todo.todo_back.entities.Task;
import com.todo.todo_back.repositories.TaskRepository;

@Service
public class TaskService {
    
    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Optional<Task> findTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public Iterable<Task> findTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public boolean deleteTask(Long taskId) {
        Optional<Task> taskFromDB = taskRepository.findById(taskId);

        if (taskFromDB.isPresent()) {
            taskRepository.deleteById(taskId);
            return true;
        }

        return false;
    }

}
