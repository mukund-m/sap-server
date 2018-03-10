package com.changeapp.repository;

import com.changeapp.domain.FieldDefinition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FieldDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldDefinitionRepository extends JpaRepository<FieldDefinition,Long> {
    
}
