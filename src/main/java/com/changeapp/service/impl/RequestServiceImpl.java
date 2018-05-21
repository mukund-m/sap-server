package com.changeapp.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changeapp.domain.PeopleRoleUserMapping;
import com.changeapp.domain.Request;
import com.changeapp.domain.Task;
import com.changeapp.domain.TaskQuestionInstance;
import com.changeapp.domain.TaskStructureConfig;
import com.changeapp.domain.TreeNode;
import com.changeapp.domain.User;
import com.changeapp.repository.RequestRepository;
import com.changeapp.repository.TaskQuestionInstanceRepository;
import com.changeapp.repository.TaskStructureConfigRepository;
import com.changeapp.security.SecurityUtils;
import com.changeapp.service.PeopleRoleUserMappingService;
import com.changeapp.service.RequestService;
import com.changeapp.service.TaskQuestionInstanceService;
import com.changeapp.service.UserService;

/**
 * Service Implementation for managing Request.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService{

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;
    private final TaskStructureConfigRepository taskStructureConfigRepository;
    private final TaskQuestionInstanceService taskQuestionInstanceRepository;
    private final UserService userService;
    private final PeopleRoleUserMappingService peopleRoleUserMappingService;


    public RequestServiceImpl(RequestRepository requestRepository, TaskStructureConfigRepository taskStructureConfigRepository,
    		TaskQuestionInstanceService taskQuestionInstanceRepository, UserService userService, PeopleRoleUserMappingService peopleRoleUserMappingService) {
        this.requestRepository = requestRepository;
        this.taskStructureConfigRepository = taskStructureConfigRepository;
        this.taskQuestionInstanceRepository = taskQuestionInstanceRepository;
        this.userService = userService;
        this.peopleRoleUserMappingService = peopleRoleUserMappingService;
    }

    /**
     * Save a request.
     *
     * @param request the entity to save
     * @return the persisted entity
     */
    @Override
    public Request save(Request request) {
        log.debug("Request to save Request : {}", request);
        return requestRepository.save(request);
    }

    /**
     *  Get all the requests.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Request> findAll() {
        log.debug("Request to get all Requests");
        return requestRepository.findAll();
    }

    /**
     *  Get one request by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Request findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findOne(id);
    }

    /**
     *  Delete the  request by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.delete(id);
    }

	@Override
	public void createInitialTasks(Request actualRequest) {
		List<TaskStructureConfig> taskStructures = this.taskStructureConfigRepository.findAll();
		List<TaskStructureConfig> list = new ArrayList<>();
		for(TaskStructureConfig config: taskStructures) {
			if(config.getRequestTypeDefConfig().getRequestType().equals(actualRequest.getType())) {
				//get structure if exist
				list.add(config);
			}
		}
		TaskStructureConfig initial = null;
		List<TaskStructureConfig> initalList = new ArrayList<>();
		for(TaskStructureConfig config2: list) {
			if(config2.getOrder()!=null ) {
				initial = config2;
			//	break;
				if(initial.getType().equals("STRUCTURE") && initial.getOrder() == 1) {
					//find all tasks with parent id as this
					for(TaskStructureConfig config: list) {
						if(config.getParentID() != null) {
							int parentId = Integer.parseInt(config.getParentID().toString());
							int id = Integer.parseInt(initial.getId().toString());
							
							if(  parentId == id && config.getType().equals("TASK")) {
								initalList.add(config);
								//search for qns under this task
								/*for(TaskStructureConfig config1: list ) {
									if(config1.getParentID() != null) {
										int parentId1 = Integer.parseInt(config1.getParentID().toString());
										int id1 = Integer.parseInt(config.getId().toString());
										if(  parentId1==id1 && config1.getType().equals("QUESTION")) {
											initalList.add(config1);
										}
									}
									
								}*/
							}
						}
						
					}
				}
				else if(initial.getType().equals("TASK")) {
					//find all tasks with parent id as this
					//initalList.add(initial);
							/*for(TaskStructureConfig config1: list ) {
								if(config1.getParentID() != null ) {
									int parentId1 = Integer.parseInt(config1.getParentID().toString());
									int id1 = Integer.parseInt(initial.getParentID().toString());
									if( parentId1 == id1 && config1.getType().equals("QUESTION")) {
										initalList.add(config1);
									}
								}
									
						}*/
				}
			}
		}
		//initalList.add(initial);
		
		//ADD TASKS AND QNS to instance
		for(TaskStructureConfig config: initalList) {
			if(config.getType().equals("TASK")) {
				TaskQuestionInstance instance = new TaskQuestionInstance();
				instance.setRequest(actualRequest);
				instance.setDefinitionID(config.getId());
				Date input = new Date();
				Instant instant = input.toInstant();
				ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
				LocalDate notifiedDate = zdt.toLocalDate();
				instance.setNotifiedDate(notifiedDate);
				instance.setDueDate(notifiedDate);
				instance.setStatus("INITIATED");
				instance = this.taskQuestionInstanceRepository.save(instance);
				for(TaskStructureConfig config2: initalList) {
					if(config2.getParentID() != null ) {
						int parentId = Integer.parseInt(config2.getParentID().toString());
						int id = Integer.parseInt(config.getId().toString());
						if( parentId == id && config2.getType().equals("QUESTION")) {
							TaskQuestionInstance instance1 = new TaskQuestionInstance();
							instance1.setRequest(actualRequest);;
							instance1.setDefinitionID(config2.getId());
							instance1.setParentID(instance.getId());
							this.taskQuestionInstanceRepository.save(instance1);
						}
					}
					
				}
			}
		}
		
	}
	
	@Transactional
	public List<TreeNode> getTasksForRequest(Request request) {
		List<TreeNode> nodes = new ArrayList<>();
		//get all tasks for request
		for(TaskQuestionInstance instance: this.taskQuestionInstanceRepository.findAll()) {
			if(instance.getRequest() !=null && instance.getRequest().getId().equals(request.getId())) {
				//find if parent is present, if its a structure, add it as a parent
				if(instance.getParentID() == null) { // then its a task
					
					TaskStructureConfig config = this.taskStructureConfigRepository.findOne(instance.getDefinitionID());
					
					if(config.getParentID() != null) {
						TreeNode node = null ;
						TaskStructureConfig config1 = this.taskStructureConfigRepository.findOne(Long.parseLong(config.getParentID().toString()));
						//check if parent already exist
						if(this.getParentNode(nodes, config1.getId()) != null) {
							node = this.getParentNode(nodes, config1.getId());
							TreeNode child = new TreeNode();
							child.setType("Task");
							child.setStatus(instance.getStatus());
							child.setResp(null);
							child.setNotifiedDate(instance.getNotifiedDate());
							child.setName(config.getName());
							child.setId(instance.getId());
							child.setDueDate(instance.getDueDate());
							child.setCompletedDate(instance.getCompletedDate());
							node.getChildren().add(child);
							
						} else {
							if(config1!=null && config1.getType().equals("STRUCTURE")) {
								node = new TreeNode();
								node.setType("Structure");
								
								node.setName(config1.getName());
								node.setId(config1.getId());
								nodes.add(node);
								TreeNode child = new TreeNode();
								child.setType("Task");
								child.setStatus(instance.getStatus());
								child.setResp(null);
								child.setNotifiedDate(instance.getNotifiedDate());
								child.setName(config.getName());
								child.setId(instance.getId());
								child.setDueDate(instance.getDueDate());
								child.setCompletedDate(instance.getCompletedDate());
								node.getChildren().add(child);
								
							} else {
								node = new TreeNode();
								node.setType("Task");
								node.setStatus(instance.getStatus());
								node.setResp(null);
								node.setNotifiedDate(instance.getNotifiedDate());
								node.setName(config.getName());
								node.setId(instance.getId());
								node.setDueDate(instance.getDueDate());
								node.setCompletedDate(instance.getCompletedDate());
								node.setName(config1.getName());
								node.setId(config1.getId());
								nodes.add(node);
							}
						}
						
						
						
						
						
					}
				}
				
			}
		}
		return nodes;
	}
	
	private TreeNode getParentNode(List<TreeNode> nodes, Long id) {
		for(TreeNode node: nodes) {
			if(node.getId().equals(id)) {
				return node;
			}
		}
		return null;
	}

	@Override
	public List<Task> getMyTasks(Long role) {
		List<Task> tasks = new ArrayList<>();
		for(TaskQuestionInstance taskQuestionInstance: this.taskQuestionInstanceRepository.findAll()) {
			TaskStructureConfig config = this.taskStructureConfigRepository.findOne(taskQuestionInstance.getDefinitionID());
			if((taskQuestionInstance.getStatus() == null || taskQuestionInstance.getStatus().equals("INITIATED")) && taskQuestionInstance.getDefinitionID() !=null && config != null &&config.getPeopleRole() != null && config.getPeopleRole() == Integer.parseInt(role.toString())) {
				Task task = new Task();
				task.setName(config.getName());
				task.setInstruction(config.getInstruction());
				task.setId(taskQuestionInstance.getId());
				task.setConfigId(taskQuestionInstance.getDefinitionID());
				task.setCompletedDate(taskQuestionInstance.getCompletedDate());
				task.setDueDate(taskQuestionInstance.getDueDate());
				task.setNotifedDate(taskQuestionInstance.getNotifiedDate());
				task.setRequest(taskQuestionInstance.getRequest());
				tasks.add(task)
				;
			}
		}
		return tasks;
	}

	@Override
	public Task getTask(Long id) {
		String userName = SecurityUtils.getCurrentUserLogin();
    	User user = this.userService.getUserWithAuthoritiesByLogin(userName).orElse(null);
    	Long role = 0L;
    	for(PeopleRoleUserMapping peopleRoleUserMapping: this.peopleRoleUserMappingService.findAll()) {
    		if(peopleRoleUserMapping.getUserID().equals(user.getLogin())) {
    			role = peopleRoleUserMapping.getId();
    		}
    	}
    	//get all tasks for the role
    	List<Task> tasks = this.getMyTasks(role);
    	Task task1 = null;
    	for(Task task: tasks) {
    		if(task.getId().longValue() == id.longValue()) {
    			task1 = task;
    			break;
    		}
    	}
    	List<TaskStructureConfig> qns = new ArrayList<>();
    	for(TaskStructureConfig config: this.taskStructureConfigRepository.findAll()) {
    		if(config.getParentID() != null && config.getParentID().intValue()== task1.getConfigId().longValue() && config.getType().equals("QUESTION")) {
    			qns.add(config);
    		}
    	}
    	task1.setQuestion(qns);
    	return task1;
	}

	@Override
	public void saveTaskQuestions(Long taskId, HashMap<Long, String> map) {
		TaskQuestionInstance instance;
    	for(Long id: map.keySet()) {
    		
    		instance = new TaskQuestionInstance();
    		instance.setParentID(taskId);
    		instance.setDefinitionID(id);
    		instance.setQuestionResponse(map.get(id));
    		boolean isUpdate = false;
    		for(TaskQuestionInstance inst: this.taskQuestionInstanceRepository.findAll()) {
    			if(inst.getParentID()!=null&& inst.getDefinitionID()!=null && inst.getParentID().equals(taskId) && inst.getDefinitionID().equals(id)) {
    				inst.setQuestionResponse(map.get(id));
    				this.taskQuestionInstanceRepository.save(inst);
    				isUpdate = true;
    				break;
    			}
    		}
    		if(!isUpdate) {
    			this.taskQuestionInstanceRepository.save(instance);
    		}
    	}
	}
	
	@Override
	public void createRemainingTasks(Request actualRequest) {
		List<TaskStructureConfig> taskStructures = this.taskStructureConfigRepository.findAll();
		List<TaskStructureConfig> list = new ArrayList<>();
		for(TaskStructureConfig config: taskStructures) {
			if(config.getRequestTypeDefConfig().getRequestType().equals(actualRequest.getType())) {
				//get structure if exist
				list.add(config);
			}
		}
		TaskStructureConfig initial = null;
		List<TaskStructureConfig> initalList = new ArrayList<>();
		for(TaskStructureConfig config2: list) {
			if(config2.getOrder()!=null ) {
				initial = config2;
			//	break;
				if(initial.getType().equals("STRUCTURE") && initial.getOrder() != 1) {
					//find all tasks with parent id as this
					for(TaskStructureConfig config: list) {
						if(config.getParentID() != null) {
							int parentId = Integer.parseInt(config.getParentID().toString());
							int id = Integer.parseInt(initial.getId().toString());
							
							if(  parentId == id && config.getType().equals("TASK")) {
								initalList.add(config);
								
							}
						}
						
					}
				}
			}
		}
		//initalList.add(initial);
		
		//ADD TASKS AND QNS to instance
		for(TaskStructureConfig config: initalList) {
			if(config.getType().equals("TASK")) {
				TaskQuestionInstance instance = new TaskQuestionInstance();
				instance.setRequest(actualRequest);
				instance.setDefinitionID(config.getId());
				Date input = new Date();
				Instant instant = input.toInstant();
				ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
				LocalDate notifiedDate = zdt.toLocalDate();
				instance.setNotifiedDate(notifiedDate);
				instance.setDueDate(notifiedDate);
				instance.setStatus("INITIATED");
				instance = this.taskQuestionInstanceRepository.save(instance);
				for(TaskStructureConfig config2: initalList) {
					if(config2.getParentID() != null ) {
						int parentId = Integer.parseInt(config2.getParentID().toString());
						int id = Integer.parseInt(config.getId().toString());
						if( parentId == id && config2.getType().equals("QUESTION")) {
							TaskQuestionInstance instance1 = new TaskQuestionInstance();
							instance1.setRequest(actualRequest);;
							instance1.setDefinitionID(config2.getId());
							instance1.setParentID(instance.getId());
							this.taskQuestionInstanceRepository.save(instance1);
						}
					}
					
				}
			}
		}
		
	}
	

	
}
