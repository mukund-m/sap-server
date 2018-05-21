package com.changeapp.service.impl;

import com.changeapp.service.TaskQuestionInstanceService;
import com.changeapp.domain.TaskQuestionInstance;
import com.changeapp.repository.TaskQuestionInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing TaskQuestionInstance.
 */
@Service
@Transactional
public class TaskQuestionInstanceServiceImpl implements TaskQuestionInstanceService{

    private final Logger log = LoggerFactory.getLogger(TaskQuestionInstanceServiceImpl.class);

    private final TaskQuestionInstanceRepository taskQuestionInstanceRepository;

    public TaskQuestionInstanceServiceImpl(TaskQuestionInstanceRepository taskQuestionInstanceRepository) {
        this.taskQuestionInstanceRepository = taskQuestionInstanceRepository;
    }

    /**
     * Save a taskQuestionInstance.
     *
     * @param taskQuestionInstance the entity to save
     * @return the persisted entity
     */
    @Override
    public TaskQuestionInstance save(TaskQuestionInstance taskQuestionInstance) {
        log.debug("Request to save TaskQuestionInstance : {}", taskQuestionInstance);
        return taskQuestionInstanceRepository.save(taskQuestionInstance);
    }

    /**
     *  Get all the taskQuestionInstances.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskQuestionInstance> findAll() {
        log.debug("Request to get all TaskQuestionInstances");
        return taskQuestionInstanceRepository.findAll();
    }

    /**
     *  Get one taskQuestionInstance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TaskQuestionInstance findOne(Long id) {
        log.debug("Request to get TaskQuestionInstance : {}", id);
        return taskQuestionInstanceRepository.findOne(id);
    }

    /**
     *  Delete the  taskQuestionInstance by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskQuestionInstance : {}", id);
        taskQuestionInstanceRepository.delete(id);
    }
}
