package com.todo.todo_back.web_controllers;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.todo.todo_back.entities.Task;
import com.todo.todo_back.entities.User;
import com.todo.todo_back.services.TaskService;
import com.todo.todo_back.services.UserService;

@RestController
public class TaskController {

    static final String TASK_NOT_FOUND_MESSAGE = "Task not found";

	TaskService taskService;
    UserService userService;

	public TaskController(TaskService taskService, UserService userService) {
		this.taskService = taskService;
        this.userService = userService;
	}

	@GetMapping("/getTasks")
	public Iterable<Task> getTasks(
        @RequestParam Optional<Boolean> isStatusFilter,
        @RequestParam Optional<ZonedDateTime> dueGreaterThanFilter,
        @RequestParam Optional<ZonedDateTime> dueLessThanFilter,
        @RequestParam Optional<String> titleLikeFilter,
        @RequestParam(defaultValue = "TITLE") Task.Fields sortingField,
        @RequestParam(defaultValue = "true") Boolean isAscending,
        Authentication authentication
    ) {
        User user = getCurrentUser(authentication);
        
        return taskService.findAll(
            isStatusFilter, 
            dueGreaterThanFilter, 
            dueLessThanFilter, 
            titleLikeFilter, 
            Optional.of(user), 
            sortingField, 
            isAscending
        );
	}

    @GetMapping("/getTask/{taskId}")
	public Task getTask(@PathVariable Long taskId, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Task task = taskService.findTaskById(taskId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TASK_NOT_FOUND_MESSAGE));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view this task");
        }

        return task;
	}

    @PostMapping("/addTask")
    public void addTask(@RequestBody @Validated Task task, Authentication authentication) {
        User user = getCurrentUser(authentication);

        task.setUser(user);
        taskService.saveTask(task);
    }

    @DeleteMapping("/deleteTask/{taskId}")
    public void deleteTask(@PathVariable Long taskId, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Task task = taskService.findTaskById(taskId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TASK_NOT_FOUND_MESSAGE));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to delete this task");
        }

        taskService.deleteTask(task.getId());
    }

    @PutMapping("/updateTask/{taskId}")
    public void updateTask(@PathVariable Long taskId, @RequestBody @Validated Task task, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Task foundTask = taskService.findTaskById(taskId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TASK_NOT_FOUND_MESSAGE));

        if (!foundTask.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to update this task");
        }

        task.setId(taskId);
        taskService.saveTask(task);
    }

    private User getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return userService.findUserByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

}
