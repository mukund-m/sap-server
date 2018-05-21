package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.PeopleRoleUserMapping;
import com.changeapp.service.PeopleRoleUserMappingService;
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
 * REST controller for managing PeopleRoleUserMapping.
 */
@RestController
@RequestMapping("/api")
public class PeopleRoleUserMappingResource {

    private final Logger log = LoggerFactory.getLogger(PeopleRoleUserMappingResource.class);

    private static final String ENTITY_NAME = "peopleRoleUserMapping";

    private final PeopleRoleUserMappingService peopleRoleUserMappingService;

    public PeopleRoleUserMappingResource(PeopleRoleUserMappingService peopleRoleUserMappingService) {
        this.peopleRoleUserMappingService = peopleRoleUserMappingService;
    }

    /**
     * POST  /people-role-user-mappings : Create a new peopleRoleUserMapping.
     *
     * @param peopleRoleUserMapping the peopleRoleUserMapping to create
     * @return the ResponseEntity with status 201 (Created) and with body the new peopleRoleUserMapping, or with status 400 (Bad Request) if the peopleRoleUserMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/people-role-user-mappings")
    @Timed
    public ResponseEntity<PeopleRoleUserMapping> createPeopleRoleUserMapping(@RequestBody PeopleRoleUserMapping peopleRoleUserMapping) throws URISyntaxException {
        log.debug("REST request to save PeopleRoleUserMapping : {}", peopleRoleUserMapping);
        if (peopleRoleUserMapping.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new peopleRoleUserMapping cannot already have an ID")).body(null);
        }
        PeopleRoleUserMapping result = peopleRoleUserMappingService.save(peopleRoleUserMapping);
        return ResponseEntity.created(new URI("/api/people-role-user-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /people-role-user-mappings : Updates an existing peopleRoleUserMapping.
     *
     * @param peopleRoleUserMapping the peopleRoleUserMapping to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated peopleRoleUserMapping,
     * or with status 400 (Bad Request) if the peopleRoleUserMapping is not valid,
     * or with status 500 (Internal Server Error) if the peopleRoleUserMapping couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/people-role-user-mappings")
    @Timed
    public ResponseEntity<PeopleRoleUserMapping> updatePeopleRoleUserMapping(@RequestBody PeopleRoleUserMapping peopleRoleUserMapping) throws URISyntaxException {
        log.debug("REST request to update PeopleRoleUserMapping : {}", peopleRoleUserMapping);
        if (peopleRoleUserMapping.getId() == null) {
            return createPeopleRoleUserMapping(peopleRoleUserMapping);
        }
        PeopleRoleUserMapping result = peopleRoleUserMappingService.save(peopleRoleUserMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, peopleRoleUserMapping.getId().toString()))
            .body(result);
    }

    /**
     * GET  /people-role-user-mappings : get all the peopleRoleUserMappings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of peopleRoleUserMappings in body
     */
    @GetMapping("/people-role-user-mappings")
    @Timed
    public List<PeopleRoleUserMapping> getAllPeopleRoleUserMappings() {
        log.debug("REST request to get all PeopleRoleUserMappings");
        return peopleRoleUserMappingService.findAll();
    }

    /**
     * GET  /people-role-user-mappings/:id : get the "id" peopleRoleUserMapping.
     *
     * @param id the id of the peopleRoleUserMapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the peopleRoleUserMapping, or with status 404 (Not Found)
     */
    @GetMapping("/people-role-user-mappings/{id}")
    @Timed
    public ResponseEntity<PeopleRoleUserMapping> getPeopleRoleUserMapping(@PathVariable Long id) {
        log.debug("REST request to get PeopleRoleUserMapping : {}", id);
        PeopleRoleUserMapping peopleRoleUserMapping = peopleRoleUserMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(peopleRoleUserMapping));
    }

    /**
     * DELETE  /people-role-user-mappings/:id : delete the "id" peopleRoleUserMapping.
     *
     * @param id the id of the peopleRoleUserMapping to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/people-role-user-mappings/{id}")
    @Timed
    public ResponseEntity<Void> deletePeopleRoleUserMapping(@PathVariable Long id) {
        log.debug("REST request to delete PeopleRoleUserMapping : {}", id);
        peopleRoleUserMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
