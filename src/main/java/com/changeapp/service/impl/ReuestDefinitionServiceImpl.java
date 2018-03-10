package com.changeapp.service.impl;

import com.changeapp.service.ReuestDefinitionService;
import com.changeapp.domain.ReuestDefinition;
import com.changeapp.repository.ReuestDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ReuestDefinition.
 */
@Service
@Transactional
public class ReuestDefinitionServiceImpl implements ReuestDefinitionService{

    private final Logger log = LoggerFactory.getLogger(ReuestDefinitionServiceImpl.class);

    private final ReuestDefinitionRepository reuestDefinitionRepository;

    public ReuestDefinitionServiceImpl(ReuestDefinitionRepository reuestDefinitionRepository) {
        this.reuestDefinitionRepository = reuestDefinitionRepository;
    }

    /**
     * Save a reuestDefinition.
     *
     * @param reuestDefinition the entity to save
     * @return the persisted entity
     */
    @Override
    public ReuestDefinition save(ReuestDefinition reuestDefinition) {
        log.debug("Request to save ReuestDefinition : {}", reuestDefinition);
        return reuestDefinitionRepository.save(reuestDefinition);
    }

    /**
     *  Get all the reuestDefinitions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReuestDefinition> findAll() {
        log.debug("Request to get all ReuestDefinitions");
        return reuestDefinitionRepository.findAll();
    }

    /**
     *  Get one reuestDefinition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReuestDefinition findOne(Long id) {
        log.debug("Request to get ReuestDefinition : {}", id);
        return reuestDefinitionRepository.findOne(id);
    }

    /**
     *  Delete the  reuestDefinition by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReuestDefinition : {}", id);
        reuestDefinitionRepository.delete(id);
    }
}
