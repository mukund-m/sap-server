package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.FieldDefinition;
import com.changeapp.domain.RequestTypeDefConfig;
import com.changeapp.service.RequestTypeDefConfigService;
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
 * REST controller for managing RequestTypeDefConfig.
 */
@RestController
@RequestMapping("/api")
public class RequestTypeDefConfigResource {

    private final Logger log = LoggerFactory.getLogger(RequestTypeDefConfigResource.class);

    private static final String ENTITY_NAME = "requestTypeDefConfig";

    private final RequestTypeDefConfigService requestTypeDefConfigService;

    public RequestTypeDefConfigResource(RequestTypeDefConfigService requestTypeDefConfigService) {
        this.requestTypeDefConfigService = requestTypeDefConfigService;
    }

    /**
     * POST  /request-type-def-configs : Create a new requestTypeDefConfig.
     *
     * @param requestTypeDefConfig the requestTypeDefConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestTypeDefConfig, or with status 400 (Bad Request) if the requestTypeDefConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-type-def-configs")
    @Timed
    public ResponseEntity<RequestTypeDefConfig> createRequestTypeDefConfig(@RequestBody RequestTypeDefConfig requestTypeDefConfig) throws URISyntaxException {
        log.debug("REST request to save RequestTypeDefConfig : {}", requestTypeDefConfig);
        if (requestTypeDefConfig.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new requestTypeDefConfig cannot already have an ID")).body(null);
        }
        RequestTypeDefConfig result = requestTypeDefConfigService.save(requestTypeDefConfig);
        return ResponseEntity.created(new URI("/api/request-type-def-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-type-def-configs : Updates an existing requestTypeDefConfig.
     *
     * @param requestTypeDefConfig the requestTypeDefConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestTypeDefConfig,
     * or with status 400 (Bad Request) if the requestTypeDefConfig is not valid,
     * or with status 500 (Internal Server Error) if the requestTypeDefConfig couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-type-def-configs")
    @Timed
    public ResponseEntity<RequestTypeDefConfig> updateRequestTypeDefConfig(@RequestBody RequestTypeDefConfig requestTypeDefConfig) throws URISyntaxException {
        log.debug("REST request to update RequestTypeDefConfig : {}", requestTypeDefConfig);
        if (requestTypeDefConfig.getId() == null) {
            return createRequestTypeDefConfig(requestTypeDefConfig);
        }
        RequestTypeDefConfig result = requestTypeDefConfigService.save(requestTypeDefConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestTypeDefConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-type-def-configs : get all the requestTypeDefConfigs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requestTypeDefConfigs in body
     */
    @GetMapping("/request-type-def-configs")
    @Timed
    public List<RequestTypeDefConfig> getAllRequestTypeDefConfigs() {
        log.debug("REST request to get all RequestTypeDefConfigs");
        return requestTypeDefConfigService.findAll();
    }

    /**
     * GET  /request-type-def-configs/:id : get the "id" requestTypeDefConfig.
     *
     * @param id the id of the requestTypeDefConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestTypeDefConfig, or with status 404 (Not Found)
     */
    @GetMapping("/request-type-def-configs/{id}")
    @Timed
    public ResponseEntity<RequestTypeDefConfig> getRequestTypeDefConfig(@PathVariable Long id) {
        log.debug("REST request to get RequestTypeDefConfig : {}", id);
        RequestTypeDefConfig requestTypeDefConfig = requestTypeDefConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestTypeDefConfig));
    }
    
    @GetMapping("/request-type-def-fields/{type}")
    @Timed
    public ResponseEntity<List<FieldDefinition>> getDefFields(@PathVariable String type) {
        
        List<FieldDefinition> requestTypeDefConfig = requestTypeDefConfigService.getDefFields(type);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestTypeDefConfig));
    }

    /**
     * DELETE  /request-type-def-configs/:id : delete the "id" requestTypeDefConfig.
     *
     * @param id the id of the requestTypeDefConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-type-def-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestTypeDefConfig(@PathVariable Long id) {
        log.debug("REST request to delete RequestTypeDefConfig : {}", id);
        requestTypeDefConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
