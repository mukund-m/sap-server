package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.RequestAttachment;
import com.changeapp.service.RequestAttachmentService;
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
 * REST controller for managing RequestAttachment.
 */
@RestController
@RequestMapping("/api")
public class RequestAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(RequestAttachmentResource.class);

    private static final String ENTITY_NAME = "requestAttachment";

    private final RequestAttachmentService requestAttachmentService;

    public RequestAttachmentResource(RequestAttachmentService requestAttachmentService) {
        this.requestAttachmentService = requestAttachmentService;
    }

    /**
     * POST  /request-attachments : Create a new requestAttachment.
     *
     * @param requestAttachment the requestAttachment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestAttachment, or with status 400 (Bad Request) if the requestAttachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-attachments")
    @Timed
    public ResponseEntity<RequestAttachment> createRequestAttachment(@RequestBody RequestAttachment requestAttachment) throws URISyntaxException {
        log.debug("REST request to save RequestAttachment : {}", requestAttachment);
        if (requestAttachment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new requestAttachment cannot already have an ID")).body(null);
        }
        RequestAttachment result = requestAttachmentService.save(requestAttachment);
        return ResponseEntity.created(new URI("/api/request-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-attachments : Updates an existing requestAttachment.
     *
     * @param requestAttachment the requestAttachment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestAttachment,
     * or with status 400 (Bad Request) if the requestAttachment is not valid,
     * or with status 500 (Internal Server Error) if the requestAttachment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-attachments")
    @Timed
    public ResponseEntity<RequestAttachment> updateRequestAttachment(@RequestBody RequestAttachment requestAttachment) throws URISyntaxException {
        log.debug("REST request to update RequestAttachment : {}", requestAttachment);
        if (requestAttachment.getId() == null) {
            return createRequestAttachment(requestAttachment);
        }
        RequestAttachment result = requestAttachmentService.save(requestAttachment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestAttachment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-attachments : get all the requestAttachments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requestAttachments in body
     */
    @GetMapping("/request-attachments")
    @Timed
    public List<RequestAttachment> getAllRequestAttachments() {
        log.debug("REST request to get all RequestAttachments");
        return requestAttachmentService.findAll();
    }

    /**
     * GET  /request-attachments/:id : get the "id" requestAttachment.
     *
     * @param id the id of the requestAttachment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestAttachment, or with status 404 (Not Found)
     */
    @GetMapping("/request-attachments/{id}")
    @Timed
    public ResponseEntity<RequestAttachment> getRequestAttachment(@PathVariable Long id) {
        log.debug("REST request to get RequestAttachment : {}", id);
        RequestAttachment requestAttachment = requestAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestAttachment));
    }

    /**
     * DELETE  /request-attachments/:id : delete the "id" requestAttachment.
     *
     * @param id the id of the requestAttachment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-attachments/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestAttachment(@PathVariable Long id) {
        log.debug("REST request to delete RequestAttachment : {}", id);
        requestAttachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
