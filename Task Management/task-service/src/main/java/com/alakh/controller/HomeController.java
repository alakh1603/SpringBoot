package com.alakh.controller;

import com.alakh.modal.Task;
import com.alakh.modal.TaskStatus;
import com.alakh.modal.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @GetMapping("/tasks")
    public ResponseEntity<String> getAssignedUsersTask() throws Exception
    {
        return new ResponseEntity<>("Welcome to Task Service", HttpStatus.OK);
    }
}
