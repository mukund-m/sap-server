package com.changeapp.service;

import com.changeapp.domain.TaskQuestionInstance;
import java.util.List;

/**
 * Service Interface for managing TaskQuestionInstance.
 */
public interface TaskQuestionInstanceService {

    /**
     * Save a taskQuestionInstance.
     *
     * @param taskQuestionInstance the entity to save
     * @return the persisted entity
     */
    TaskQuestionInstance save(TaskQuestionInstance taskQuestionInstance);

    /**
     *  Get all the taskQuestionInstances.
     *
     *  @return the list of entities
     */
    List<TaskQuestionInstance> findAll();

    /**
     *  Get the "id" taskQuestionInstance.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TaskQuestionInstance findOne(Long id);

    /**
     *  Delete the "id" taskQuestionInstance.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
