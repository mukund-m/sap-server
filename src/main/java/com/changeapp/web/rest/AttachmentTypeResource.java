package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.AttachmentType;
import com.changeapp.service.AttachmentTypeService;
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
 * REST controller for managing AttachmentType.
 */
@RestController
@RequestMapping("/api")
public class AttachmentTypeResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentTypeResource.class);

    private static final String ENTITY_NAME = "attachmentType";

    private final AttachmentTypeService attachmentTypeService;

    public AttachmentTypeResource(AttachmentTypeService attachmentTypeService) {
        this.attachmentTypeService = attachmentTypeService;
    }

    /**
     * POST  /attachment-types : Create a new attachmentType.
     *
     * @param attachmentType the attachmentType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attachmentType, or with status 400 (Bad Request) if the attachmentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attachment-types")
    @Timed
    public ResponseEntity<AttachmentType> createAttachmentType(@RequestBody AttachmentType attachmentType) throws URISyntaxException {
        log.debug("REST request to save AttachmentType : {}", attachmentType);
        if (attachmentType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new attachmentType cannot already have an ID")).body(null);
        }
        AttachmentType result = attachmentTypeService.save(attachmentType);
        return ResponseEntity.created(new URI("/api/attachment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attachment-types : Updates an existing attachmentType.
     *
     * @param attachmentType the attachmentType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attachmentType,
     * or with status 400 (Bad Request) if the attachmentType is not valid,
     * or with status 500 (Internal Server Error) if the attachmentType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attachment-types")
    @Timed
    public ResponseEntity<AttachmentType> updateAttachmentType(@RequestBody AttachmentType attachmentType) throws URISyntaxException {
        log.debug("REST request to update AttachmentType : {}", attachmentType);
        if (attachmentType.getId() == null) {
            return createAttachmentType(attachmentType);
        }
        AttachmentType result = attachmentTypeService.save(attachmentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attachmentType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attachment-types : get all the attachmentTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of attachmentTypes in body
     */
    @GetMapping("/attachment-types")
    @Timed
    public List<AttachmentType> getAllAttachmentTypes() {
        log.debug("REST request to get all AttachmentTypes");
        return attachmentTypeService.findAll();
    }

    /**
     * GET  /attachment-types/:id : get the "id" attachmentType.
     *
     * @param id the id of the attachmentType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attachmentType, or with status 404 (Not Found)
     */
    @GetMapping("/attachment-types/{id}")
    @Timed
    public ResponseEntity<AttachmentType> getAttachmentType(@PathVariable Long id) {
        log.debug("REST request to get AttachmentType : {}", id);
        AttachmentType attachmentType = attachmentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(attachmentType));
    }

    /**
     * DELETE  /attachment-types/:id : delete the "id" attachmentType.
     *
     * @param id the id of the attachmentType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attachment-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttachmentType(@PathVariable Long id) {
        log.debug("REST request to delete AttachmentType : {}", id);
        attachmentTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
