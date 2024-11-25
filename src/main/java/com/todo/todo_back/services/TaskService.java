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
            .where(isStatusFilter.isPresent() ? TaskSpecification.isStatus(isStatusFilter.get()) : null)
            .and(dueGreaterThanFilter.isPresent() ? TaskSpecification.dueGreaterThan(dueGreaterThanFilter.get()) : null)
            .and(dueLessThanFilter.isPresent() ? TaskSpecification.dueLessThan(dueLessThanFilter.get()) : null)
            .and(titleLikeFilter.isPresent() ? TaskSpecification.titleLike(titleLikeFilter.get()) : null)
            .and(userEqualFilter.isPresent() ? TaskSpecification.userEqual(userEqualFilter.get()) : null);

        Sort sort = Sort.by(isAscending == false ? Sort.Direction.DESC : Sort.Direction.ASC, sortingField.getDatabaseFieldName());

        return taskRepository.findAll(filters, sort);
    }

}
