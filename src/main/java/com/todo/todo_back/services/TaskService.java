package com.todo.todo_back.services;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.todo.todo_back.entities.Task;
import com.todo.todo_back.entities.User;
import com.todo.todo_back.repositories.TaskRepository;
import com.todo.todo_back.specifications.TaskSpecification;

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

    public boolean deleteTask(Long taskId) {
        Optional<Task> taskFromDB = taskRepository.findById(taskId);

        if (taskFromDB.isPresent()) {
            taskRepository.deleteById(taskId);
            return true;
        }

        return false;
    }

    public Iterable<Task> findAll(Optional<Boolean> isStatusFilter, 
        Optional<ZonedDateTime> dueGreaterThanFilter,
        Optional<ZonedDateTime> dueLessThanFilter,
        Optional<String> titleLikeFilter,
        Optional<User> userEqualFilter,
        Task.Fields sortingField,
        Boolean isAscending
    ) {
        Specification<Task> filters = Specification
            .where(isStatusFilter.map(TaskSpecification::isStatus).orElse(null))
            .and(dueGreaterThanFilter.map(TaskSpecification::dueGreaterThan).orElse(null))
            .and(dueLessThanFilter.map(TaskSpecification::dueLessThan).orElse(null))
            .and(titleLikeFilter.map(TaskSpecification::titleLike).orElse(null))
            .and(userEqualFilter.map(TaskSpecification::userEqual).orElse(null));

        Sort sort = Sort.by(Boolean.TRUE.equals(isAscending) ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField.getDatabaseFieldName());

        return taskRepository.findAll(filters, sort);
    }

}
