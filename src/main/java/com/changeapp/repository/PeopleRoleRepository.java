package com.changeapp.repository;

import com.changeapp.domain.PeopleRole;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PeopleRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeopleRoleRepository extends JpaRepository<PeopleRole,Long> {
    
}
