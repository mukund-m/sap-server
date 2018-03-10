package com.changeapp.service;

import com.changeapp.domain.RequestAttachment;
import java.util.List;

/**
 * Service Interface for managing RequestAttachment.
 */
public interface RequestAttachmentService {

    /**
     * Save a requestAttachment.
     *
     * @param requestAttachment the entity to save
     * @return the persisted entity
     */
    RequestAttachment save(RequestAttachment requestAttachment);

    /**
     *  Get all the requestAttachments.
     *
     *  @return the list of entities
     */
    List<RequestAttachment> findAll();

    /**
     *  Get the "id" requestAttachment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RequestAttachment findOne(Long id);

    /**
     *  Delete the "id" requestAttachment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
