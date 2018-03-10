package com.changeapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A DefinitionConfig.
 */
@Entity
@Table(name = "definition_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DefinitionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "def_name")
    private String defName;

    @OneToMany(mappedBy = "definition")
    @JsonIgnore
    private List<FieldDefinition> fieldConfigs = new ArrayList();

    @OneToOne(mappedBy = "definition")
    @JsonIgnore
    private RequestTypeDefConfig reqType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefName() {
        return defName;
    }

    public DefinitionConfig defName(String defName) {
        this.defName = defName;
        return this;
    }

    public void setDefName(String defName) {
        this.defName = defName;
    }

    public List<FieldDefinition> getFieldConfigs() {
        return fieldConfigs;
    }

    public DefinitionConfig fieldConfigs(List<FieldDefinition> fieldDefinitions) {
        this.fieldConfigs = fieldDefinitions;
        return this;
    }

    public DefinitionConfig addFieldConfigs(FieldDefinition fieldDefinition) {
        this.fieldConfigs.add(fieldDefinition);
        fieldDefinition.setDefinition(this);
        return this;
    }

    public DefinitionConfig removeFieldConfigs(FieldDefinition fieldDefinition) {
        this.fieldConfigs.remove(fieldDefinition);
        fieldDefinition.setDefinition(null);
        return this;
    }

    public void setFieldConfigs(List<FieldDefinition> fieldDefinitions) {
        this.fieldConfigs = fieldDefinitions;
    }

    public RequestTypeDefConfig getReqType() {
        return reqType;
    }

    public DefinitionConfig reqType(RequestTypeDefConfig requestTypeDefConfig) {
        this.reqType = requestTypeDefConfig;
        return this;
    }

    public void setReqType(RequestTypeDefConfig requestTypeDefConfig) {
        this.reqType = requestTypeDefConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefinitionConfig definitionConfig = (DefinitionConfig) o;
        if (definitionConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), definitionConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DefinitionConfig{" +
            "id=" + getId() +
            ", defName='" + getDefName() + "'" +
            "}";
    }
}
