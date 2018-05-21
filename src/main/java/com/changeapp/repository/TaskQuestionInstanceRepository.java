package com.changeapp.repository;

import com.changeapp.domain.TaskQuestionInstance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TaskQuestionInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskQuestionInstanceRepository extends JpaRepository<TaskQuestionInstance,Long> {
    
}
