package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.ReuestDefinition;
import com.changeapp.service.ReuestDefinitionService;
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
 * REST controller for managing ReuestDefinition.
 */
@RestController
@RequestMapping("/api")
public class ReuestDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(ReuestDefinitionResource.class);

    private static final String ENTITY_NAME = "reuestDefinition";

    private final ReuestDefinitionService reuestDefinitionService;

    public ReuestDefinitionResource(ReuestDefinitionService reuestDefinitionService) {
        this.reuestDefinitionService = reuestDefinitionService;
    }

    /**
     * POST  /reuest-definitions : Create a new reuestDefinition.
     *
     * @param reuestDefinition the reuestDefinition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reuestDefinition, or with status 400 (Bad Request) if the reuestDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reuest-definitions")
    @Timed
    public ResponseEntity<ReuestDefinition> createReuestDefinition(@RequestBody ReuestDefinition reuestDefinition) throws URISyntaxException {
        log.debug("REST request to save ReuestDefinition : {}", reuestDefinition);
        if (reuestDefinition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reuestDefinition cannot already have an ID")).body(null);
        }
        ReuestDefinition result = reuestDefinitionService.save(reuestDefinition);
        return ResponseEntity.created(new URI("/api/reuest-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reuest-definitions : Updates an existing reuestDefinition.
     *
     * @param reuestDefinition the reuestDefinition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reuestDefinition,
     * or with status 400 (Bad Request) if the reuestDefinition is not valid,
     * or with status 500 (Internal Server Error) if the reuestDefinition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reuest-definitions")
    @Timed
    public ResponseEntity<ReuestDefinition> updateReuestDefinition(@RequestBody ReuestDefinition reuestDefinition) throws URISyntaxException {
        log.debug("REST request to update ReuestDefinition : {}", reuestDefinition);
        if (reuestDefinition.getId() == null) {
            return createReuestDefinition(reuestDefinition);
        }
        ReuestDefinition result = reuestDefinitionService.save(reuestDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reuestDefinition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reuest-definitions : get all the reuestDefinitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reuestDefinitions in body
     */
    @GetMapping("/reuest-definitions")
    @Timed
    public List<ReuestDefinition> getAllReuestDefinitions() {
        log.debug("REST request to get all ReuestDefinitions");
        return reuestDefinitionService.findAll();
    }

    /**
     * GET  /reuest-definitions/:id : get the "id" reuestDefinition.
     *
     * @param id the id of the reuestDefinition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reuestDefinition, or with status 404 (Not Found)
     */
    @GetMapping("/reuest-definitions/{id}")
    @Timed
    public ResponseEntity<ReuestDefinition> getReuestDefinition(@PathVariable Long id) {
        log.debug("REST request to get ReuestDefinition : {}", id);
        ReuestDefinition reuestDefinition = reuestDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reuestDefinition));
    }

    /**
     * DELETE  /reuest-definitions/:id : delete the "id" reuestDefinition.
     *
     * @param id the id of the reuestDefinition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reuest-definitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteReuestDefinition(@PathVariable Long id) {
        log.debug("REST request to delete ReuestDefinition : {}", id);
        reuestDefinitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
