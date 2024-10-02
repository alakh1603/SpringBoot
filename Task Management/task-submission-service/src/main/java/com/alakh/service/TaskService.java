package com.alakh.service;

import com.alakh.modal.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="TASK-SERVICE", url="http://localhost:5002")
public interface TaskService {

    @GetMapping("/app/tasks/{id}")
    public TaskDto getTaskById(@PathVariable Long id) throws Exception;

    @PutMapping("/app/tasks/{id}/complete")
    public TaskDto completeTask(@PathVariable Long id) throws Exception;
}
