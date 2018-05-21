package com.changeapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AttachmentType.
 */
@Entity
@Table(name = "attachment_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AttachmentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private RequestTypeDefConfig requestTypeDefConfig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public AttachmentType typeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public AttachmentType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestTypeDefConfig getRequestTypeDefConfig() {
        return requestTypeDefConfig;
    }

    public AttachmentType requestTypeDefConfig(RequestTypeDefConfig requestTypeDefConfig) {
        this.requestTypeDefConfig = requestTypeDefConfig;
        return this;
    }

    public void setRequestTypeDefConfig(RequestTypeDefConfig requestTypeDefConfig) {
        this.requestTypeDefConfig = requestTypeDefConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttachmentType attachmentType = (AttachmentType) o;
        if (attachmentType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attachmentType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttachmentType{" +
            "id=" + getId() +
            ", typeName='" + getTypeName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
