package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.DefinitionConfig;
import com.changeapp.service.DefinitionConfigService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing DefinitionConfig.
 */
@RestController
@RequestMapping("/api")
public class DefinitionConfigResource {

    private final Logger log = LoggerFactory.getLogger(DefinitionConfigResource.class);

    private static final String ENTITY_NAME = "definitionConfig";

    private final DefinitionConfigService definitionConfigService;

    public DefinitionConfigResource(DefinitionConfigService definitionConfigService) {
        this.definitionConfigService = definitionConfigService;
    }

    /**
     * POST  /definition-configs : Create a new definitionConfig.
     *
     * @param definitionConfig the definitionConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new definitionConfig, or with status 400 (Bad Request) if the definitionConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/definition-configs")
    @Timed
    public ResponseEntity<DefinitionConfig> createDefinitionConfig(@RequestBody DefinitionConfig definitionConfig) throws URISyntaxException {
        log.debug("REST request to save DefinitionConfig : {}", definitionConfig);
        if (definitionConfig.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new definitionConfig cannot already have an ID")).body(null);
        }
        DefinitionConfig result = definitionConfigService.save(definitionConfig);
        return ResponseEntity.created(new URI("/api/definition-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /definition-configs : Updates an existing definitionConfig.
     *
     * @param definitionConfig the definitionConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated definitionConfig,
     * or with status 400 (Bad Request) if the definitionConfig is not valid,
     * or with status 500 (Internal Server Error) if the definitionConfig couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/definition-configs")
    @Timed
    public ResponseEntity<DefinitionConfig> updateDefinitionConfig(@RequestBody DefinitionConfig definitionConfig) throws URISyntaxException {
        log.debug("REST request to update DefinitionConfig : {}", definitionConfig);
        if (definitionConfig.getId() == null) {
            return createDefinitionConfig(definitionConfig);
        }
        DefinitionConfig result = definitionConfigService.save(definitionConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, definitionConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /definition-configs : get all the definitionConfigs.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of definitionConfigs in body
     */
    @GetMapping("/definition-configs")
    @Timed
    public List<DefinitionConfig> getAllDefinitionConfigs(@RequestParam(required = false) String filter) {
        if ("reqtype-is-null".equals(filter)) {
            log.debug("REST request to get all DefinitionConfigs where reqType is null");
            return definitionConfigService.findAllWhereReqTypeIsNull();
        }
        log.debug("REST request to get all DefinitionConfigs");
        return definitionConfigService.findAll();
    }

    /**
     * GET  /definition-configs/:id : get the "id" definitionConfig.
     *
     * @param id the id of the definitionConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the definitionConfig, or with status 404 (Not Found)
     */
    @GetMapping("/definition-configs/{id}")
    @Timed
    public ResponseEntity<DefinitionConfig> getDefinitionConfig(@PathVariable Long id) {
        log.debug("REST request to get DefinitionConfig : {}", id);
        DefinitionConfig definitionConfig = definitionConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(definitionConfig));
    }

    /**
     * DELETE  /definition-configs/:id : delete the "id" definitionConfig.
     *
     * @param id the id of the definitionConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/definition-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDefinitionConfig(@PathVariable Long id) {
        log.debug("REST request to delete DefinitionConfig : {}", id);
        definitionConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
