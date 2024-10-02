package com.alakh.repository;

import com.alakh.modal.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByTaskId(Long taskId);
}
