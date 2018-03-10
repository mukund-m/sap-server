package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.FieldDefinition;
import com.changeapp.service.FieldDefinitionService;
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
 * REST controller for managing FieldDefinition.
 */
@RestController
@RequestMapping("/api")
public class FieldDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(FieldDefinitionResource.class);

    private static final String ENTITY_NAME = "fieldDefinition";

    private final FieldDefinitionService fieldDefinitionService;

    public FieldDefinitionResource(FieldDefinitionService fieldDefinitionService) {
        this.fieldDefinitionService = fieldDefinitionService;
    }

    /**
     * POST  /field-definitions : Create a new fieldDefinition.
     *
     * @param fieldDefinition the fieldDefinition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fieldDefinition, or with status 400 (Bad Request) if the fieldDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/field-definitions")
    @Timed
    public ResponseEntity<FieldDefinition> createFieldDefinition(@RequestBody FieldDefinition fieldDefinition) throws URISyntaxException {
        log.debug("REST request to save FieldDefinition : {}", fieldDefinition);
        if (fieldDefinition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fieldDefinition cannot already have an ID")).body(null);
        }
        FieldDefinition result = fieldDefinitionService.save(fieldDefinition);
        return ResponseEntity.created(new URI("/api/field-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /field-definitions : Updates an existing fieldDefinition.
     *
     * @param fieldDefinition the fieldDefinition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fieldDefinition,
     * or with status 400 (Bad Request) if the fieldDefinition is not valid,
     * or with status 500 (Internal Server Error) if the fieldDefinition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/field-definitions")
    @Timed
    public ResponseEntity<FieldDefinition> updateFieldDefinition(@RequestBody FieldDefinition fieldDefinition) throws URISyntaxException {
        log.debug("REST request to update FieldDefinition : {}", fieldDefinition);
        if (fieldDefinition.getId() == null) {
            return createFieldDefinition(fieldDefinition);
        }
        FieldDefinition result = fieldDefinitionService.save(fieldDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fieldDefinition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /field-definitions : get all the fieldDefinitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fieldDefinitions in body
     */
    @GetMapping("/field-definitions")
    @Timed
    public List<FieldDefinition> getAllFieldDefinitions() {
        log.debug("REST request to get all FieldDefinitions");
        return fieldDefinitionService.findAll();
    }

    /**
     * GET  /field-definitions/:id : get the "id" fieldDefinition.
     *
     * @param id the id of the fieldDefinition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fieldDefinition, or with status 404 (Not Found)
     */
    @GetMapping("/field-definitions/{id}")
    @Timed
    public ResponseEntity<FieldDefinition> getFieldDefinition(@PathVariable Long id) {
        log.debug("REST request to get FieldDefinition : {}", id);
        FieldDefinition fieldDefinition = fieldDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fieldDefinition));
    }

    /**
     * DELETE  /field-definitions/:id : delete the "id" fieldDefinition.
     *
     * @param id the id of the fieldDefinition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/field-definitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteFieldDefinition(@PathVariable Long id) {
        log.debug("REST request to delete FieldDefinition : {}", id);
        fieldDefinitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
