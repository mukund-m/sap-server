package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.RefCodeDetails;

import com.changeapp.repository.RefCodeDetailsRepository;
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
 * REST controller for managing RefCodeDetails.
 */
@RestController
@RequestMapping("/api")
public class RefCodeDetailsResource {

    private final Logger log = LoggerFactory.getLogger(RefCodeDetailsResource.class);

    private static final String ENTITY_NAME = "refCodeDetails";

    private final RefCodeDetailsRepository refCodeDetailsRepository;

    public RefCodeDetailsResource(RefCodeDetailsRepository refCodeDetailsRepository) {
        this.refCodeDetailsRepository = refCodeDetailsRepository;
    }

    /**
     * POST  /ref-code-details : Create a new refCodeDetails.
     *
     * @param refCodeDetails the refCodeDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refCodeDetails, or with status 400 (Bad Request) if the refCodeDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-code-details")
    @Timed
    public ResponseEntity<RefCodeDetails> createRefCodeDetails(@RequestBody RefCodeDetails refCodeDetails) throws URISyntaxException {
        log.debug("REST request to save RefCodeDetails : {}", refCodeDetails);
        if (refCodeDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refCodeDetails cannot already have an ID")).body(null);
        }
        RefCodeDetails result = refCodeDetailsRepository.save(refCodeDetails);
        return ResponseEntity.created(new URI("/api/ref-code-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-code-details : Updates an existing refCodeDetails.
     *
     * @param refCodeDetails the refCodeDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refCodeDetails,
     * or with status 400 (Bad Request) if the refCodeDetails is not valid,
     * or with status 500 (Internal Server Error) if the refCodeDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-code-details")
    @Timed
    public ResponseEntity<RefCodeDetails> updateRefCodeDetails(@RequestBody RefCodeDetails refCodeDetails) throws URISyntaxException {
        log.debug("REST request to update RefCodeDetails : {}", refCodeDetails);
        if (refCodeDetails.getId() == null) {
            return createRefCodeDetails(refCodeDetails);
        }
        RefCodeDetails result = refCodeDetailsRepository.save(refCodeDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refCodeDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-code-details : get all the refCodeDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of refCodeDetails in body
     */
    @GetMapping("/ref-code-details")
    @Timed
    public List<RefCodeDetails> getAllRefCodeDetails() {
        log.debug("REST request to get all RefCodeDetails");
        return refCodeDetailsRepository.findAll();
    }

    /**
     * GET  /ref-code-details/:id : get the "id" refCodeDetails.
     *
     * @param id the id of the refCodeDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refCodeDetails, or with status 404 (Not Found)
     */
    @GetMapping("/ref-code-details/{id}")
    @Timed
    public ResponseEntity<RefCodeDetails> getRefCodeDetails(@PathVariable Long id) {
        log.debug("REST request to get RefCodeDetails : {}", id);
        RefCodeDetails refCodeDetails = refCodeDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refCodeDetails));
    }

    /**
     * DELETE  /ref-code-details/:id : delete the "id" refCodeDetails.
     *
     * @param id the id of the refCodeDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-code-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefCodeDetails(@PathVariable Long id) {
        log.debug("REST request to delete RefCodeDetails : {}", id);
        refCodeDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
