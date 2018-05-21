package com.changeapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changeapp.domain.RequestTypeDefConfig;
import com.changeapp.domain.TaskStructureConfig;
import com.changeapp.repository.TaskStructureConfigRepository;
import com.changeapp.service.RequestService;
import com.changeapp.service.RequestTypeDefConfigService;
import com.changeapp.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing TaskStructureConfig.
 */
@RestController
@RequestMapping("/api")
public class TaskStructureConfigResource {

    private final Logger log = LoggerFactory.getLogger(TaskStructureConfigResource.class);

    private static final String ENTITY_NAME = "taskStructureConfig";

    private final TaskStructureConfigRepository taskStructureConfigRepository;
    private final RequestService requestService;
    private final RequestTypeDefConfigService requestTypeDefConfigService;
    

    public TaskStructureConfigResource(TaskStructureConfigRepository taskStructureConfigRepository, RequestService requestService,
    		RequestTypeDefConfigService requestTypeDefConfigService) {
        this.taskStructureConfigRepository = taskStructureConfigRepository;
        this.requestService = requestService;
        this.requestTypeDefConfigService = requestTypeDefConfigService;
        
    }


    /**
     * POST  /task-structure-configs : Create a new taskStructureConfig.
     *
     * @param taskStructureConfig the taskStructureConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskStructureConfig, or with status 400 (Bad Request) if the taskStructureConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-structure-configs")
    @Timed
    public ResponseEntity<TaskStructureConfig> createTaskStructureConfig(@RequestBody TaskStructureConfig taskStructureConfig) throws URISyntaxException {
        log.debug("REST request to save TaskStructureConfig : {}", taskStructureConfig);
        if (taskStructureConfig.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new taskStructureConfig cannot already have an ID")).body(null);
        }
        TaskStructureConfig result = taskStructureConfigRepository.save(taskStructureConfig);
        return ResponseEntity.created(new URI("/api/task-structure-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-structure-configs : Updates an existing taskStructureConfig.
     *
     * @param taskStructureConfig the taskStructureConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskStructureConfig,
     * or with status 400 (Bad Request) if the taskStructureConfig is not valid,
     * or with status 500 (Internal Server Error) if the taskStructureConfig couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-structure-configs")
    @Timed
    public ResponseEntity<TaskStructureConfig> updateTaskStructureConfig(@RequestBody TaskStructureConfig taskStructureConfig) throws URISyntaxException {
        log.debug("REST request to update TaskStructureConfig : {}", taskStructureConfig);
        if (taskStructureConfig.getId() == null) {
            return createTaskStructureConfig(taskStructureConfig);
        }
        TaskStructureConfig result = taskStructureConfigRepository.save(taskStructureConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskStructureConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /task-structure-configs : get all the taskStructureConfigs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taskStructureConfigs in body
     */
    @GetMapping("/task-structure-configs")
    @Timed
    public List<TaskStructureConfig> getAllTaskStructureConfigs() {
        log.debug("REST request to get all TaskStructureConfigs");
        return taskStructureConfigRepository.findAll();
    }

    /**
     * GET  /task-structure-configs/:id : get the "id" taskStructureConfig.
     *
     * @param id the id of the taskStructureConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskStructureConfig, or with status 404 (Not Found)
     */
    @GetMapping("/task-structure-configs/{id}")
    @Timed
    public ResponseEntity<TaskStructureConfig> getTaskStructureConfig(@PathVariable Long id) {
        log.debug("REST request to get TaskStructureConfig : {}", id);
        TaskStructureConfig taskStructureConfig = taskStructureConfigRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskStructureConfig));
    }

    /**
     * DELETE  /task-structure-configs/:id : delete the "id" taskStructureConfig.
     *
     * @param id the id of the taskStructureConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-structure-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskStructureConfig(@PathVariable Long id) {
        log.debug("REST request to delete TaskStructureConfig : {}", id);
        taskStructureConfigRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/task-structures/{id}")
    public HashMap<String, Object> getTasksForRequest(@PathVariable Long id) {
    	String requestType = this.requestService.findOne(id).getType();
    	Long requestTypeId = 0L;
    	for(RequestTypeDefConfig requestTypeDefConfig: this.requestTypeDefConfigService.findAll()) {
    		if(requestType.equals(requestTypeDefConfig.getRequestType())) {
    			requestTypeId = requestTypeDefConfig.getId();
    		}
    	}
    	List<TaskStructureConfig> list = this.taskStructureConfigRepository.findAll();
    	for(TaskStructureConfig config: list) {
    		if(config.getRequestTypeDefConfig().getId().equals(requestTypeId) && config.getType().equals("STRUCTURE")) {
    			
    		}
    	}
		return null;
    	
    }

}
