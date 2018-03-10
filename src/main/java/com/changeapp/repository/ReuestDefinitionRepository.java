package com.changeapp.repository;

import com.changeapp.domain.ReuestDefinition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ReuestDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReuestDefinitionRepository extends JpaRepository<ReuestDefinition,Long> {
    
}
