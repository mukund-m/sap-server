package com.changeapp.service;

import com.changeapp.domain.PeopleRoleUserMapping;
import com.changeapp.repository.PeopleRoleUserMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing PeopleRoleUserMapping.
 */
@Service
@Transactional
public class PeopleRoleUserMappingService {

    private final Logger log = LoggerFactory.getLogger(PeopleRoleUserMappingService.class);

    private final PeopleRoleUserMappingRepository peopleRoleUserMappingRepository;

    public PeopleRoleUserMappingService(PeopleRoleUserMappingRepository peopleRoleUserMappingRepository) {
        this.peopleRoleUserMappingRepository = peopleRoleUserMappingRepository;
    }

    /**
     * Save a peopleRoleUserMapping.
     *
     * @param peopleRoleUserMapping the entity to save
     * @return the persisted entity
     */
    public PeopleRoleUserMapping save(PeopleRoleUserMapping peopleRoleUserMapping) {
        log.debug("Request to save PeopleRoleUserMapping : {}", peopleRoleUserMapping);
        return peopleRoleUserMappingRepository.save(peopleRoleUserMapping);
    }

    /**
     *  Get all the peopleRoleUserMappings.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PeopleRoleUserMapping> findAll() {
        log.debug("Request to get all PeopleRoleUserMappings");
        return peopleRoleUserMappingRepository.findAll();
    }

    /**
     *  Get one peopleRoleUserMapping by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PeopleRoleUserMapping findOne(Long id) {
        log.debug("Request to get PeopleRoleUserMapping : {}", id);
        return peopleRoleUserMappingRepository.findOne(id);
    }

    /**
     *  Delete the  peopleRoleUserMapping by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PeopleRoleUserMapping : {}", id);
        peopleRoleUserMappingRepository.delete(id);
    }
}
