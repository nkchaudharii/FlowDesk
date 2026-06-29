package com.flowdesk.repository;

import com.flowdesk.entity.Project;
import com.flowdesk.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByWorkspace(Workspace workspace);
}
