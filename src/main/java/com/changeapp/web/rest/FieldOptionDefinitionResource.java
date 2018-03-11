package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.FieldOptionDefinition;
import com.changeapp.service.FieldOptionDefinitionService;
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
 * REST controller for managing FieldOptionDefinition.
 */
@RestController
@RequestMapping("/api")
public class FieldOptionDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(FieldOptionDefinitionResource.class);

    private static final String ENTITY_NAME = "fieldOptionDefinition";

    private final FieldOptionDefinitionService fieldOptionDefinitionService;

    public FieldOptionDefinitionResource(FieldOptionDefinitionService fieldOptionDefinitionService) {
        this.fieldOptionDefinitionService = fieldOptionDefinitionService;
    }

    /**
     * POST  /field-option-definitions : Create a new fieldOptionDefinition.
     *
     * @param fieldOptionDefinition the fieldOptionDefinition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fieldOptionDefinition, or with status 400 (Bad Request) if the fieldOptionDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/field-option-definitions")
    @Timed
    public ResponseEntity<FieldOptionDefinition> createFieldOptionDefinition(@RequestBody FieldOptionDefinition fieldOptionDefinition) throws URISyntaxException {
        log.debug("REST request to save FieldOptionDefinition : {}", fieldOptionDefinition);
        if (fieldOptionDefinition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fieldOptionDefinition cannot already have an ID")).body(null);
        }
        FieldOptionDefinition result = fieldOptionDefinitionService.save(fieldOptionDefinition);
        return ResponseEntity.created(new URI("/api/field-option-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /field-option-definitions : Updates an existing fieldOptionDefinition.
     *
     * @param fieldOptionDefinition the fieldOptionDefinition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fieldOptionDefinition,
     * or with status 400 (Bad Request) if the fieldOptionDefinition is not valid,
     * or with status 500 (Internal Server Error) if the fieldOptionDefinition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/field-option-definitions")
    @Timed
    public ResponseEntity<FieldOptionDefinition> updateFieldOptionDefinition(@RequestBody FieldOptionDefinition fieldOptionDefinition) throws URISyntaxException {
        log.debug("REST request to update FieldOptionDefinition : {}", fieldOptionDefinition);
        if (fieldOptionDefinition.getId() == null) {
            return createFieldOptionDefinition(fieldOptionDefinition);
        }
        FieldOptionDefinition result = fieldOptionDefinitionService.save(fieldOptionDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fieldOptionDefinition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /field-option-definitions : get all the fieldOptionDefinitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fieldOptionDefinitions in body
     */
    @GetMapping("/field-option-definitions")
    @Timed
    public List<FieldOptionDefinition> getAllFieldOptionDefinitions() {
        log.debug("REST request to get all FieldOptionDefinitions");
        return fieldOptionDefinitionService.findAll();
    }

    /**
     * GET  /field-option-definitions/:id : get the "id" fieldOptionDefinition.
     *
     * @param id the id of the fieldOptionDefinition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fieldOptionDefinition, or with status 404 (Not Found)
     */
    @GetMapping("/field-option-definitions/{id}")
    @Timed
    public ResponseEntity<FieldOptionDefinition> getFieldOptionDefinition(@PathVariable Long id) {
        log.debug("REST request to get FieldOptionDefinition : {}", id);
        FieldOptionDefinition fieldOptionDefinition = fieldOptionDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fieldOptionDefinition));
    }

    /**
     * DELETE  /field-option-definitions/:id : delete the "id" fieldOptionDefinition.
     *
     * @param id the id of the fieldOptionDefinition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/field-option-definitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteFieldOptionDefinition(@PathVariable Long id) {
        log.debug("REST request to delete FieldOptionDefinition : {}", id);
        fieldOptionDefinitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
