package com.flowdesk.repository;

import com.flowdesk.entity.User;
import com.flowdesk.entity.Workspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Optional<Workspace> findBySlug(String slug);

    Page<Workspace> findAllByOwner(User owner, Pageable pageable);
}
