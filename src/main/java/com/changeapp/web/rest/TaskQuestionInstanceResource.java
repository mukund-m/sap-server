package com.changeapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.changeapp.domain.TaskQuestionInstance;
import com.changeapp.service.TaskQuestionInstanceService;
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
 * REST controller for managing TaskQuestionInstance.
 */
@RestController
@RequestMapping("/api")
public class TaskQuestionInstanceResource {

    private final Logger log = LoggerFactory.getLogger(TaskQuestionInstanceResource.class);

    private static final String ENTITY_NAME = "taskQuestionInstance";

    private final TaskQuestionInstanceService taskQuestionInstanceService;

    public TaskQuestionInstanceResource(TaskQuestionInstanceService taskQuestionInstanceService) {
        this.taskQuestionInstanceService = taskQuestionInstanceService;
    }

    /**
     * POST  /task-question-instances : Create a new taskQuestionInstance.
     *
     * @param taskQuestionInstance the taskQuestionInstance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskQuestionInstance, or with status 400 (Bad Request) if the taskQuestionInstance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-question-instances")
    @Timed
    public ResponseEntity<TaskQuestionInstance> createTaskQuestionInstance(@RequestBody TaskQuestionInstance taskQuestionInstance) throws URISyntaxException {
        log.debug("REST request to save TaskQuestionInstance : {}", taskQuestionInstance);
        if (taskQuestionInstance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new taskQuestionInstance cannot already have an ID")).body(null);
        }
        TaskQuestionInstance result = taskQuestionInstanceService.save(taskQuestionInstance);
        return ResponseEntity.created(new URI("/api/task-question-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-question-instances : Updates an existing taskQuestionInstance.
     *
     * @param taskQuestionInstance the taskQuestionInstance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskQuestionInstance,
     * or with status 400 (Bad Request) if the taskQuestionInstance is not valid,
     * or with status 500 (Internal Server Error) if the taskQuestionInstance couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-question-instances")
    @Timed
    public ResponseEntity<TaskQuestionInstance> updateTaskQuestionInstance(@RequestBody TaskQuestionInstance taskQuestionInstance) throws URISyntaxException {
        log.debug("REST request to update TaskQuestionInstance : {}", taskQuestionInstance);
        if (taskQuestionInstance.getId() == null) {
            return createTaskQuestionInstance(taskQuestionInstance);
        }
        TaskQuestionInstance result = taskQuestionInstanceService.save(taskQuestionInstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskQuestionInstance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /task-question-instances : get all the taskQuestionInstances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taskQuestionInstances in body
     */
    @GetMapping("/task-question-instances")
    @Timed
    public List<TaskQuestionInstance> getAllTaskQuestionInstances() {
        log.debug("REST request to get all TaskQuestionInstances");
        return taskQuestionInstanceService.findAll();
    }

    /**
     * GET  /task-question-instances/:id : get the "id" taskQuestionInstance.
     *
     * @param id the id of the taskQuestionInstance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskQuestionInstance, or with status 404 (Not Found)
     */
    @GetMapping("/task-question-instances/{id}")
    @Timed
    public ResponseEntity<TaskQuestionInstance> getTaskQuestionInstance(@PathVariable Long id) {
        log.debug("REST request to get TaskQuestionInstance : {}", id);
        TaskQuestionInstance taskQuestionInstance = taskQuestionInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskQuestionInstance));
    }

    /**
     * DELETE  /task-question-instances/:id : delete the "id" taskQuestionInstance.
     *
     * @param id the id of the taskQuestionInstance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-question-instances/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskQuestionInstance(@PathVariable Long id) {
        log.debug("REST request to delete TaskQuestionInstance : {}", id);
        taskQuestionInstanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
