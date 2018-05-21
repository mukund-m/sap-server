package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.FieldChoiceDefinition;
import com.changeapp.service.FieldChoiceDefinitionService;
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
 * REST controller for managing FieldChoiceDefinition.
 */
@RestController
@RequestMapping("/api")
public class FieldChoiceDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(FieldChoiceDefinitionResource.class);

    private static final String ENTITY_NAME = "fieldChoiceDefinition";

    private final FieldChoiceDefinitionService fieldChoiceDefinitionService;

    public FieldChoiceDefinitionResource(FieldChoiceDefinitionService fieldChoiceDefinitionService) {
        this.fieldChoiceDefinitionService = fieldChoiceDefinitionService;
    }

    /**
     * POST  /field-choice-definitions : Create a new fieldChoiceDefinition.
     *
     * @param fieldChoiceDefinition the fieldChoiceDefinition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fieldChoiceDefinition, or with status 400 (Bad Request) if the fieldChoiceDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/field-choice-definitions")
    @Timed
    public ResponseEntity<FieldChoiceDefinition> createFieldChoiceDefinition(@RequestBody FieldChoiceDefinition fieldChoiceDefinition) throws URISyntaxException {
        log.debug("REST request to save FieldChoiceDefinition : {}", fieldChoiceDefinition);
        if (fieldChoiceDefinition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fieldChoiceDefinition cannot already have an ID")).body(null);
        }
        FieldChoiceDefinition result = fieldChoiceDefinitionService.save(fieldChoiceDefinition);
        return ResponseEntity.created(new URI("/api/field-choice-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /field-choice-definitions : Updates an existing fieldChoiceDefinition.
     *
     * @param fieldChoiceDefinition the fieldChoiceDefinition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fieldChoiceDefinition,
     * or with status 400 (Bad Request) if the fieldChoiceDefinition is not valid,
     * or with status 500 (Internal Server Error) if the fieldChoiceDefinition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/field-choice-definitions")
    @Timed
    public ResponseEntity<FieldChoiceDefinition> updateFieldChoiceDefinition(@RequestBody FieldChoiceDefinition fieldChoiceDefinition) throws URISyntaxException {
        log.debug("REST request to update FieldChoiceDefinition : {}", fieldChoiceDefinition);
        if (fieldChoiceDefinition.getId() == null) {
            return createFieldChoiceDefinition(fieldChoiceDefinition);
        }
        FieldChoiceDefinition result = fieldChoiceDefinitionService.save(fieldChoiceDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fieldChoiceDefinition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /field-choice-definitions : get all the fieldChoiceDefinitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fieldChoiceDefinitions in body
     */
    @GetMapping("/field-choice-definitions")
    @Timed
    public List<FieldChoiceDefinition> getAllFieldChoiceDefinitions() {
        log.debug("REST request to get all FieldChoiceDefinitions");
        return fieldChoiceDefinitionService.findAll();
    }

    /**
     * GET  /field-choice-definitions/:id : get the "id" fieldChoiceDefinition.
     *
     * @param id the id of the fieldChoiceDefinition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fieldChoiceDefinition, or with status 404 (Not Found)
     */
    @GetMapping("/field-choice-definitions/{id}")
    @Timed
    public ResponseEntity<FieldChoiceDefinition> getFieldChoiceDefinition(@PathVariable Long id) {
        log.debug("REST request to get FieldChoiceDefinition : {}", id);
        FieldChoiceDefinition fieldChoiceDefinition = fieldChoiceDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fieldChoiceDefinition));
    }

    /**
     * DELETE  /field-choice-definitions/:id : delete the "id" fieldChoiceDefinition.
     *
     * @param id the id of the fieldChoiceDefinition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/field-choice-definitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteFieldChoiceDefinition(@PathVariable Long id) {
        log.debug("REST request to delete FieldChoiceDefinition : {}", id);
        fieldChoiceDefinitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
