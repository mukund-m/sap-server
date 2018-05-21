package com.changeapp.repository;

import com.changeapp.domain.PeopleRoleUserMapping;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PeopleRoleUserMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeopleRoleUserMappingRepository extends JpaRepository<PeopleRoleUserMapping,Long> {
    
}
