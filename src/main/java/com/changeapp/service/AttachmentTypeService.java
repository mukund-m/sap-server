package com.changeapp.service;

import com.changeapp.domain.AttachmentType;
import com.changeapp.repository.AttachmentTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing AttachmentType.
 */
@Service
@Transactional
public class AttachmentTypeService {

    private final Logger log = LoggerFactory.getLogger(AttachmentTypeService.class);

    private final AttachmentTypeRepository attachmentTypeRepository;

    public AttachmentTypeService(AttachmentTypeRepository attachmentTypeRepository) {
        this.attachmentTypeRepository = attachmentTypeRepository;
    }

    /**
     * Save a attachmentType.
     *
     * @param attachmentType the entity to save
     * @return the persisted entity
     */
    public AttachmentType save(AttachmentType attachmentType) {
        log.debug("Request to save AttachmentType : {}", attachmentType);
        return attachmentTypeRepository.save(attachmentType);
    }

    /**
     *  Get all the attachmentTypes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AttachmentType> findAll() {
        log.debug("Request to get all AttachmentTypes");
        return attachmentTypeRepository.findAll();
    }

    /**
     *  Get one attachmentType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AttachmentType findOne(Long id) {
        log.debug("Request to get AttachmentType : {}", id);
        return attachmentTypeRepository.findOne(id);
    }

    /**
     *  Delete the  attachmentType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AttachmentType : {}", id);
        attachmentTypeRepository.delete(id);
    }
}
