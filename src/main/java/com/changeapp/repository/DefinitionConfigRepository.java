package com.changeapp.repository;

import com.changeapp.domain.DefinitionConfig;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DefinitionConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefinitionConfigRepository extends JpaRepository<DefinitionConfig,Long> {
    
}
