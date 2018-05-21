package com.changeapp.service;

import com.changeapp.domain.PeopleRole;
import com.changeapp.repository.PeopleRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing PeopleRole.
 */
@Service
@Transactional
public class PeopleRoleService {

    private final Logger log = LoggerFactory.getLogger(PeopleRoleService.class);

    private final PeopleRoleRepository peopleRoleRepository;

    public PeopleRoleService(PeopleRoleRepository peopleRoleRepository) {
        this.peopleRoleRepository = peopleRoleRepository;
    }

    /**
     * Save a peopleRole.
     *
     * @param peopleRole the entity to save
     * @return the persisted entity
     */
    public PeopleRole save(PeopleRole peopleRole) {
        log.debug("Request to save PeopleRole : {}", peopleRole);
        return peopleRoleRepository.save(peopleRole);
    }

    /**
     *  Get all the peopleRoles.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PeopleRole> findAll() {
        log.debug("Request to get all PeopleRoles");
        return peopleRoleRepository.findAll();
    }

    /**
     *  Get one peopleRole by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PeopleRole findOne(Long id) {
        log.debug("Request to get PeopleRole : {}", id);
        return peopleRoleRepository.findOne(id);
    }

    /**
     *  Delete the  peopleRole by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PeopleRole : {}", id);
        peopleRoleRepository.delete(id);
    }
}
