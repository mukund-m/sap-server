package com.changeapp.repository;

import com.changeapp.domain.RefCodeDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefCodeDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefCodeDetailsRepository extends JpaRepository<RefCodeDetails,Long> {
    
}
