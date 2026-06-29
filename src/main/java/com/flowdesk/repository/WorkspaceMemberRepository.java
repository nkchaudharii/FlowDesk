package com.flowdesk.repository;

import com.flowdesk.entity.User;
import com.flowdesk.entity.Workspace;
import com.flowdesk.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
    boolean existsByWorkspaceAndUser(Workspace workspace, User user);

    Optional<WorkspaceMember> findByWorkspaceAndUser(Workspace workspace, User user);

    List<WorkspaceMember> findAllByWorkspace(Workspace workspace);
}
