package com.changeapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A RequestAttachment.
 */
@Entity
@Table(name = "request_attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RequestAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "uploaded_by")
    private String uploadedBy;

    @Column(name = "uploaded_on")
    private LocalDate uploadedOn;

    @Column(name = "attachment_type")
    private String attachmentType;

    @ManyToOne
    private Request requestID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public RequestAttachment fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public RequestAttachment uploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
        return this;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public LocalDate getUploadedOn() {
        return uploadedOn;
    }

    public RequestAttachment uploadedOn(LocalDate uploadedOn) {
        this.uploadedOn = uploadedOn;
        return this;
    }

    public void setUploadedOn(LocalDate uploadedOn) {
        this.uploadedOn = uploadedOn;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public RequestAttachment attachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
        return this;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Request getRequestID() {
        return requestID;
    }

    public RequestAttachment requestID(Request request) {
        this.requestID = request;
        return this;
    }

    public void setRequestID(Request request) {
        this.requestID = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestAttachment requestAttachment = (RequestAttachment) o;
        if (requestAttachment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestAttachment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestAttachment{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", uploadedBy='" + getUploadedBy() + "'" +
            ", uploadedOn='" + getUploadedOn() + "'" +
            ", attachmentType='" + getAttachmentType() + "'" +
            "}";
    }
}
