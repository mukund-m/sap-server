package com.changeapp.repository;

import com.changeapp.domain.TaskStructureConfig;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TaskStructureConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskStructureConfigRepository extends JpaRepository<TaskStructureConfig,Long> {
    
}
