package com.alakh.controller;

import com.alakh.modal.Task;
import com.alakh.modal.TaskStatus;
import com.alakh.modal.UserDto;
import com.alakh.service.TaskService;
import com.alakh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/tasks") // should be /api/tasks but getting 403 forbidden error

public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String jwt) throws Exception
    {
        UserDto user = userService.getUSerProfile(jwt);

        Task createdTask = taskService.createTask(task, user.getRole());

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) throws Exception
    {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUsersTask(@RequestParam(required = false)TaskStatus status, @RequestHeader("Authorization") String jwt) throws Exception
    {
        UserDto user = userService.getUSerProfile(jwt);

        List<Task> tasks = taskService.assignedUsersTask(user.getId(),status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Task>> getAllTask(@RequestParam(required = false)TaskStatus status, @RequestHeader("Authorization") String jwt) throws Exception
    {
        List<Task> tasks = taskService.getAllTask(status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userid}/assigned")
    public ResponseEntity<Task> assignedTaskToUser(@PathVariable Long id, @PathVariable Long userid) throws Exception
    {
        Task task = taskService.assignedToUser(userid, id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task req, @RequestHeader("Authorization") String jwt) throws Exception
    {
        UserDto user = userService.getUSerProfile(jwt);

        Task task = taskService.updateTask(id, req, user.getId());

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) throws Exception
    {
        Task task = taskService.completeTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) throws Exception
    {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
