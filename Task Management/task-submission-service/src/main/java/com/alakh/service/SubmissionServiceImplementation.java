package com.alakh.service;

import com.alakh.modal.Submission;
import com.alakh.modal.TaskDto;
import com.alakh.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImplementation implements SubmissionService{

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TaskService taskService;

    @Override
    public Submission submitTask(Long taskId, Long userId, String githubLink) throws Exception {
        TaskDto task = taskService.getTaskById(taskId);

        if(task!= null)
        {
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithubLink(githubLink);
            submission.setSubmissionTime(LocalDateTime.now());

            return submissionRepository.save(submission);
        }
        throw new Exception("No task found with id: "+taskId);
    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submissionRepository.findById(submissionId).orElseThrow(()-> new Exception("Task submission not found with id: "+submissionId));
    }

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionsByTaskId(Long taskId) {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {

        Submission submission = getTaskSubmissionById(id);
        submission.setStatus(status);
        if(status.equals("ACCEPTED"))
        {
            taskService.completeTask(submission.getTaskId());
        }

        return submissionRepository.save(submission);
    }
}
