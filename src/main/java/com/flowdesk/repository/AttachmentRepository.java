package com.flowdesk.repository;

import com.flowdesk.entity.Attachment;
import com.flowdesk.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findAllByTask(Task task);
}
