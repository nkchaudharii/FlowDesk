package com.flowdesk.repository;

import com.flowdesk.entity.Project;
import com.flowdesk.entity.Task;
import com.flowdesk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findAllByProject(Project project);

    Page<Task> findAllByProject(Project project, Pageable pageable);

    List<Task> findAllByAssignee(User assignee);
}
