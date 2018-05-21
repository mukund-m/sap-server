package com.changeapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

import com.changeapp.domain.PeopleRoleUserMapping;
import com.changeapp.domain.Request;
import com.changeapp.domain.Task;
import com.changeapp.domain.TaskQuestionInstance;
import com.changeapp.domain.TaskStructureConfig;
import com.changeapp.domain.TreeNode;
import com.changeapp.domain.User;
import com.changeapp.repository.TaskStructureConfigRepository;
import com.changeapp.security.SecurityUtils;
import com.changeapp.service.PeopleRoleService;
import com.changeapp.service.PeopleRoleUserMappingService;
import com.changeapp.service.RequestService;
import com.changeapp.service.TaskQuestionInstanceService;
import com.changeapp.service.UserService;
import com.changeapp.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Request.
 */
@RestController
@RequestMapping("/api")
public class RequestResource {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    private final RequestService requestService;
    private final UserService userService;
    private final PeopleRoleUserMappingService peopleRoleUserMappingService;
    private final TaskQuestionInstanceService taskQuestionInstanceService;
    private final TaskStructureConfigRepository taskStructureConfigRepository;

    public RequestResource(RequestService requestService, UserService userService, PeopleRoleUserMappingService peopleRoleUserMappingService,
    		TaskQuestionInstanceService taskQuestionInstanceService, TaskStructureConfigRepository taskStructureConfigRepository) {
        this.requestService = requestService;
        this.userService = userService;
        this.taskQuestionInstanceService = taskQuestionInstanceService;
        this.peopleRoleUserMappingService = peopleRoleUserMappingService;
        this.taskStructureConfigRepository = taskStructureConfigRepository;
    }

    /**
     * POST  /requests : Create a new request.
     *
     * @param request the request to create
     * @return the ResponseEntity with status 201 (Created) and with body the new request, or with status 400 (Bad Request) if the request has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requests")
    @Timed
    public ResponseEntity<Request> createRequest(@RequestBody Request request) throws URISyntaxException {
        log.debug("REST request to save Request : {}", request);
        if (request.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new request cannot already have an ID")).body(null);
        }
        request.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        request.setCreatedOn(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        request.setStatus("SAVED");
        Request result = requestService.save(request);
        return ResponseEntity.created(new URI("/api/requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /requests : Updates an existing request.
     *
     * @param request the request to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated request,
     * or with status 400 (Bad Request) if the request is not valid,
     * or with status 500 (Internal Server Error) if the request couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/requests")
    @Timed
    public ResponseEntity<Request> updateRequest(@RequestBody Request request) throws URISyntaxException {
        log.debug("REST request to update Request : {}", request);
        if (request.getId() == null) {
            return createRequest(request);
        }
        Request result = requestService.save(request);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, request.getId().toString()))
            .body(result);
    }

    /**
     * GET  /requests : get all the requests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requests in body
     */
    @GetMapping("/requests")
    @Timed
    public List<Request> getAllRequests() {
        log.debug("REST request to get all Requests");
        return requestService.findAll();
    }

    /**
     * GET  /requests/:id : get the "id" request.
     *
     * @param id the id of the request to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the request, or with status 404 (Not Found)
     */
    @GetMapping("/requests/{id}")
    @Timed
    public ResponseEntity<Request> getRequest(@PathVariable Long id) {
        log.debug("REST request to get Request : {}", id);
        Request request = requestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(request));
    }

    /**
     * DELETE  /requests/:id : delete the "id" request.
     *
     * @param id the id of the request to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        log.debug("REST request to delete Request : {}", id);
        requestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @PostMapping("/requests/submit")
    public ResponseEntity<Request> submitRequest(@RequestBody HashMap<String, Long> request) {
    	Long requestID = request.get("requestId");
    	Request actualRequest = this.requestService.findOne(requestID);
    	actualRequest.setStatus("SUBMITTED");
    	this.requestService.save(actualRequest);
    	this.requestService.createInitialTasks(actualRequest);
    	
    	return null;
    }
    
    @PostMapping("/requests/tasks")
    public List<TreeNode> getTasksForRequest(@RequestBody HashMap<String, Long> request) {
    	Long requestID = request.get("requestId");
    	Request actualRequest = this.requestService.findOne(requestID);
    	return this.requestService.getTasksForRequest(actualRequest);
    	
    }
    
    
    
    @GetMapping("/getMyTasks")
    public List<Task> getMyTasks() {
    	String userName = SecurityUtils.getCurrentUserLogin();
    	User user = this.userService.getUserWithAuthoritiesByLogin(userName).orElse(null);
    	Long role = 0L;
    	for(PeopleRoleUserMapping peopleRoleUserMapping: this.peopleRoleUserMappingService.findAll()) {
    		if(peopleRoleUserMapping.getUserID().equals(user.getLogin())) {
    			role = peopleRoleUserMapping.getId();
    		}
    	}
    	//get all tasks for the role
    	List<Task> tasks = this.requestService.getMyTasks(role);
    	return tasks;
    }
    
    @GetMapping("/getMyRequests")
    public List<Request> getMyRequests() {
    	String userName = SecurityUtils.getCurrentUserLogin();
    	List<Request> requests = new ArrayList<>();
    	for(Request request: this.requestService.findAll()) {
    		if(request.getCreatedBy().equals(userName)) {
    			requests.add(request);
    		}
    	}
    	return requests;
    }
    
    @GetMapping("/getTask/{id}")
    public Task getTask(@PathVariable("id") Long id) {
    	return this.requestService.getTask(id);
    }
    @PostMapping("/requests/saveTask/{id}")
    public boolean saveTask(@PathVariable("id") Long taskId, @RequestBody HashMap<Long, String> map) {
    	this.requestService.saveTaskQuestions(taskId, map);
    	
    	
    	return true;
    }
    
    @PostMapping("/requests/approveTask/{id}")
    public boolean approveTask(@PathVariable("id") Long taskId, @RequestBody HashMap<Long, String> map) {
    	TaskQuestionInstance instance = this.taskQuestionInstanceService.findOne(taskId);
    	
    	instance.completedDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    	instance.setStatus("APPROVED");
    	this.taskQuestionInstanceService.save(instance);
    	TaskStructureConfig taskStructureConfig = this.taskStructureConfigRepository.findOne(instance.getDefinitionID());
    	if(taskStructureConfig.getParentID() != null) {
    		TaskStructureConfig parent = this.taskStructureConfigRepository.findOne(Long.valueOf(taskStructureConfig.getParentID().toString()));
    		if(parent.getOrder().equals(1)) {
    			this.requestService.createRemainingTasks(instance.getRequest());
    		}
    	}
    	
    	return true;

    }
    
    @PostMapping("/requests/rejectTask/{id}")
    public boolean rejectTask(@PathVariable("id") Long taskId, @RequestBody HashMap<Long, String> map) {
    	TaskQuestionInstance instance = this.taskQuestionInstanceService.findOne(taskId);
    	
    	instance.completedDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    	instance.setStatus("REJECTED");
    	this.taskQuestionInstanceService.save(instance);
    	
    	
    	return true;

    }
    
    
}
