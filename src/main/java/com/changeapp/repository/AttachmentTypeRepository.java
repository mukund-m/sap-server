package com.changeapp.repository;

import com.changeapp.domain.AttachmentType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AttachmentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentTypeRepository extends JpaRepository<AttachmentType,Long> {
    
}
