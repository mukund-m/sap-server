package com.changeapp.service;

import java.util.HashMap;
import java.util.List;

import com.changeapp.domain.Request;
import com.changeapp.domain.Task;
import com.changeapp.domain.TaskQuestionInstance;
import com.changeapp.domain.TreeNode;

/**
 * Service Interface for managing Request.
 */
public interface RequestService {

    /**
     * Save a request.
     *
     * @param request the entity to save
     * @return the persisted entity
     */
    Request save(Request request);

    /**
     *  Get all the requests.
     *
     *  @return the list of entities
     */
    List<Request> findAll();

    /**
     *  Get the "id" request.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Request findOne(Long id);

    /**
     *  Delete the "id" request.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

	void createInitialTasks(Request actualRequest);
	
	public List<TreeNode> getTasksForRequest(Request request);

	List<Task> getMyTasks(Long role);

	Task getTask(Long id);

	void saveTaskQuestions(Long taskId, HashMap<Long, String> map);

	void createRemainingTasks(Request actualRequest);
}

