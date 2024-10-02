package com.alakh.service;

import com.alakh.modal.Submission;

import java.util.*;

public interface SubmissionService {

    Submission submitTask(Long taskId, Long userId, String githubLink) throws Exception;

    Submission getTaskSubmissionById(Long submissionId) throws Exception;

    List<Submission> getAllSubmissions();

    List<Submission> getTaskSubmissionsByTaskId(Long taskId);

    Submission acceptDeclineSubmission(Long id, String status) throws Exception;
}
