package com.alakh.repository;

import com.alakh.modal.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findByAssignedUserId(Long userId);
}
