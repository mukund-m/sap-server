package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.PeopleRole;
import com.changeapp.service.PeopleRoleService;
import com.changeapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PeopleRole.
 */
@RestController
@RequestMapping("/api")
public class PeopleRoleResource {

    private final Logger log = LoggerFactory.getLogger(PeopleRoleResource.class);

    private static final String ENTITY_NAME = "peopleRole";

    private final PeopleRoleService peopleRoleService;

    public PeopleRoleResource(PeopleRoleService peopleRoleService) {
        this.peopleRoleService = peopleRoleService;
    }

    /**
     * POST  /people-roles : Create a new peopleRole.
     *
     * @param peopleRole the peopleRole to create
     * @return the ResponseEntity with status 201 (Created) and with body the new peopleRole, or with status 400 (Bad Request) if the peopleRole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/people-roles")
    @Timed
    public ResponseEntity<PeopleRole> createPeopleRole(@RequestBody PeopleRole peopleRole) throws URISyntaxException {
        log.debug("REST request to save PeopleRole : {}", peopleRole);
        if (peopleRole.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new peopleRole cannot already have an ID")).body(null);
        }
        PeopleRole result = peopleRoleService.save(peopleRole);
        return ResponseEntity.created(new URI("/api/people-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /people-roles : Updates an existing peopleRole.
     *
     * @param peopleRole the peopleRole to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated peopleRole,
     * or with status 400 (Bad Request) if the peopleRole is not valid,
     * or with status 500 (Internal Server Error) if the peopleRole couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/people-roles")
    @Timed
    public ResponseEntity<PeopleRole> updatePeopleRole(@RequestBody PeopleRole peopleRole) throws URISyntaxException {
        log.debug("REST request to update PeopleRole : {}", peopleRole);
        if (peopleRole.getId() == null) {
            return createPeopleRole(peopleRole);
        }
        PeopleRole result = peopleRoleService.save(peopleRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, peopleRole.getId().toString()))
            .body(result);
    }

    /**
     * GET  /people-roles : get all the peopleRoles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of peopleRoles in body
     */
    @GetMapping("/people-roles")
    @Timed
    public List<PeopleRole> getAllPeopleRoles() {
        log.debug("REST request to get all PeopleRoles");
        return peopleRoleService.findAll();
    }

    /**
     * GET  /people-roles/:id : get the "id" peopleRole.
     *
     * @param id the id of the peopleRole to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the peopleRole, or with status 404 (Not Found)
     */
    @GetMapping("/people-roles/{id}")
    @Timed
    public ResponseEntity<PeopleRole> getPeopleRole(@PathVariable Long id) {
        log.debug("REST request to get PeopleRole : {}", id);
        PeopleRole peopleRole = peopleRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(peopleRole));
    }

    /**
     * DELETE  /people-roles/:id : delete the "id" peopleRole.
     *
     * @param id the id of the peopleRole to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/people-roles/{id}")
    @Timed
    public ResponseEntity<Void> deletePeopleRole(@PathVariable Long id) {
        log.debug("REST request to delete PeopleRole : {}", id);
        peopleRoleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
