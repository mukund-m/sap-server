package com.changeapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RequestTypeDefConfig.
 */
@Entity
@Table(name = "request_type_def_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RequestTypeDefConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_type")
    private String requestType;

    @OneToOne
    @JoinColumn(unique = true)
    private DefinitionConfig definition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestType() {
        return requestType;
    }

    public RequestTypeDefConfig requestType(String requestType) {
        this.requestType = requestType;
        return this;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public DefinitionConfig getDefinition() {
        return definition;
    }

    public RequestTypeDefConfig definition(DefinitionConfig definitionConfig) {
        this.definition = definitionConfig;
        return this;
    }

    public void setDefinition(DefinitionConfig definitionConfig) {
        this.definition = definitionConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestTypeDefConfig requestTypeDefConfig = (RequestTypeDefConfig) o;
        if (requestTypeDefConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestTypeDefConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestTypeDefConfig{" +
            "id=" + getId() +
            ", requestType='" + getRequestType() + "'" +
            "}";
    }
}
