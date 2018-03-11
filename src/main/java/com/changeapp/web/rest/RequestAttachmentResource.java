package com.changeapp.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.changeapp.domain.RequestAttachment;
import com.changeapp.security.SecurityUtils;
import com.changeapp.service.RequestAttachmentService;
import com.changeapp.service.RequestService;
import com.changeapp.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing RequestAttachment.
 */
@RestController
@RequestMapping("/api")
public class RequestAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(RequestAttachmentResource.class);

    private static final String ENTITY_NAME = "requestAttachment";

    private final RequestAttachmentService requestAttachmentService;
    private final RequestService requestService;

    public RequestAttachmentResource(RequestAttachmentService requestAttachmentService, RequestService requestService) {
        this.requestAttachmentService = requestAttachmentService;
        this.requestService = requestService;
    }

    /**
     * POST  /request-attachments : Create a new requestAttachment.
     *
     * @param requestAttachment the requestAttachment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestAttachment, or with status 400 (Bad Request) if the requestAttachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-attachments")
    @Timed
    public ResponseEntity<RequestAttachment> createRequestAttachment(@RequestBody RequestAttachment requestAttachment) throws URISyntaxException {
        log.debug("REST request to save RequestAttachment : {}", requestAttachment);
        if (requestAttachment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new requestAttachment cannot already have an ID")).body(null);
        }
        RequestAttachment result = requestAttachmentService.save(requestAttachment);
        return ResponseEntity.created(new URI("/api/request-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    @PostMapping("/upload")
    public RequestAttachment handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("requestID") Long reqId) {
    	
    	try {
    		RequestAttachment atchment = new RequestAttachment();
        	
        	CommonsMultipartFile multipartFile = (CommonsMultipartFile) file;
        	atchment.setAttachmentType(multipartFile.getContentType());
        	atchment.setFileName(multipartFile.getOriginalFilename());
        	atchment.setUploadedBy(SecurityUtils.getCurrentUserLogin());
        	atchment.setRequestID(this.requestService.findOne(reqId));
        	atchment.setUploadedOn(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        	RequestAttachment output = this.requestAttachmentService.save(atchment);
        	File outputFile = new File("attachments/"+output.getId());
        	multipartFile.transferTo(outputFile);
        	System.out.println(multipartFile.getOriginalFilename());
        	return output;
    	}catch(Exception ex) {
    		return null;
    	}
    	
    }
    
    @RequestMapping(value = "downloadFile/{id}", method = RequestMethod.GET)
    public StreamingResponseBody getSteamingFile(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
    	RequestAttachment output = this.requestAttachmentService.findOne(id);
    	
        response.setContentType(output.getAttachmentType());
        response.setHeader("Content-Disposition", "attachment; filename=\""+output.getFileName()+"\"");
        InputStream inputStream = new FileInputStream(new File("attachments/"+output.getId()));
        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                
                outputStream.write(data, 0, nRead);
            }
        };
    }

    /**
     * PUT  /request-attachments : Updates an existing requestAttachment.
     *
     * @param requestAttachment the requestAttachment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestAttachment,
     * or with status 400 (Bad Request) if the requestAttachment is not valid,
     * or with status 500 (Internal Server Error) if the requestAttachment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-attachments")
    @Timed
    public ResponseEntity<RequestAttachment> updateRequestAttachment(@RequestBody RequestAttachment requestAttachment) throws URISyntaxException {
        log.debug("REST request to update RequestAttachment : {}", requestAttachment);
        if (requestAttachment.getId() == null) {
            return createRequestAttachment(requestAttachment);
        }
        RequestAttachment result = requestAttachmentService.save(requestAttachment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestAttachment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-attachments : get all the requestAttachments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requestAttachments in body
     */
    @GetMapping("/request-attachments")
    @Timed
    public List<RequestAttachment> getAllRequestAttachments() {
        log.debug("REST request to get all RequestAttachments");
        return requestAttachmentService.findAll();
    }

    /**
     * GET  /request-attachments/:id : get the "id" requestAttachment.
     *
     * @param id the id of the requestAttachment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestAttachment, or with status 404 (Not Found)
     */
    @GetMapping("/request-attachments/{id}")
    @Timed
    public ResponseEntity<RequestAttachment> getRequestAttachment(@PathVariable Long id) {
        log.debug("REST request to get RequestAttachment : {}", id);
        RequestAttachment requestAttachment = requestAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestAttachment));
    }

    /**
     * DELETE  /request-attachments/:id : delete the "id" requestAttachment.
     *
     * @param id the id of the requestAttachment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-attachments/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestAttachment(@PathVariable Long id) {
        log.debug("REST request to delete RequestAttachment : {}", id);
        requestAttachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
