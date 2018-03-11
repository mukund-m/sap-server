package com.changeapp.repository;

import com.changeapp.domain.FieldOptionDefinition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FieldOptionDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldOptionDefinitionRepository extends JpaRepository<FieldOptionDefinition,Long> {
    
}
