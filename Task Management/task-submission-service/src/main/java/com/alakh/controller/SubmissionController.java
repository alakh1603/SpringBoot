package com.alakh.controller;

import com.alakh.modal.Submission;
import com.alakh.modal.TaskStatus;
import com.alakh.modal.UserDto;
import com.alakh.service.SubmissionService;
import com.alakh.service.TaskService;
import com.alakh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PostMapping()
    public ResponseEntity<Submission> submitTask(@RequestParam Long task_id,
                                                 @RequestParam String github_link,
                                                 @RequestHeader("Authorization") String jwt
    ) throws Exception
    {
        UserDto user = userService.getUSerProfile(jwt);
        Submission submission = submissionService.submitTask(task_id, user.getId(), github_link);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id,
                                                 @RequestHeader("Authorization") String jwt
    ) throws Exception
    {
        UserDto user = userService.getUSerProfile(jwt);
        Submission submission = submissionService.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Submission>> getAllSubmissions() throws Exception
    {
        List<Submission> submissions = submissionService.getAllSubmissions();
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getAllTaskSubmissions(@PathVariable Long taskId) throws Exception
    {
        List<Submission> submissions = submissionService.getTaskSubmissionsByTaskId(taskId);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptDeclineSubmission(@PathVariable Long id, @RequestParam("status") String status) throws Exception
    {
        Submission submission = submissionService.acceptDeclineSubmission(id, status);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }
}
