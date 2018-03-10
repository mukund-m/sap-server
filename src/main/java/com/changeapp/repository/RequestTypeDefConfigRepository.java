package com.changeapp.repository;

import com.changeapp.domain.RequestTypeDefConfig;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestTypeDefConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestTypeDefConfigRepository extends JpaRepository<RequestTypeDefConfig,Long> {
    
}
