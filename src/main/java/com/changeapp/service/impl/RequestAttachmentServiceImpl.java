package com.changeapp.service.impl;

import com.changeapp.service.RequestAttachmentService;
import com.changeapp.domain.RequestAttachment;
import com.changeapp.repository.RequestAttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing RequestAttachment.
 */
@Service
@Transactional
public class RequestAttachmentServiceImpl implements RequestAttachmentService{

    private final Logger log = LoggerFactory.getLogger(RequestAttachmentServiceImpl.class);

    private final RequestAttachmentRepository requestAttachmentRepository;

    public RequestAttachmentServiceImpl(RequestAttachmentRepository requestAttachmentRepository) {
        this.requestAttachmentRepository = requestAttachmentRepository;
    }

    /**
     * Save a requestAttachment.
     *
     * @param requestAttachment the entity to save
     * @return the persisted entity
     */
    @Override
    public RequestAttachment save(RequestAttachment requestAttachment) {
        log.debug("Request to save RequestAttachment : {}", requestAttachment);
        return requestAttachmentRepository.save(requestAttachment);
    }

    /**
     *  Get all the requestAttachments.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RequestAttachment> findAll() {
        log.debug("Request to get all RequestAttachments");
        return requestAttachmentRepository.findAll();
    }

    /**
     *  Get one requestAttachment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RequestAttachment findOne(Long id) {
        log.debug("Request to get RequestAttachment : {}", id);
        return requestAttachmentRepository.findOne(id);
    }

    /**
     *  Delete the  requestAttachment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestAttachment : {}", id);
        requestAttachmentRepository.delete(id);
    }
}
