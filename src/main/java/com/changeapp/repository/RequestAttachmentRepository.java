package com.changeapp.repository;

import com.changeapp.domain.RequestAttachment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestAttachmentRepository extends JpaRepository<RequestAttachment,Long> {
    
}
