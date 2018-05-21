package com.changeapp.repository;

import com.changeapp.domain.FieldChoiceDefinition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FieldChoiceDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldChoiceDefinitionRepository extends JpaRepository<FieldChoiceDefinition,Long> {
    
}
