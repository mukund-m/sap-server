package com.changeapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FieldDefinition.
 */
@Entity
@Table(name = "field_definition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "field_type")
    private String fieldType;

    @Column(name = "jhi_key")
    private String key;

    @Column(name = "name")
    private String name;

    @Column(name = "place_holder")
    private String placeHolder;

    @Column(name = "mandatory")
    private Boolean mandatory;

    @OneToMany(mappedBy = "fieldDefinition")
    @JsonIgnore
    private Set<ReuestDefinition> definitions = new HashSet<>();

    @ManyToOne
    private DefinitionConfig definition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldType() {
        return fieldType;
    }

    public FieldDefinition fieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getKey() {
        return key;
    }

    public FieldDefinition key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public FieldDefinition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public FieldDefinition placeHolder(String placeHolder) {
        this.placeHolder = placeHolder;
        return this;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public FieldDefinition mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Set<ReuestDefinition> getDefinitions() {
        return definitions;
    }

    public FieldDefinition definitions(Set<ReuestDefinition> reuestDefinitions) {
        this.definitions = reuestDefinitions;
        return this;
    }

    public FieldDefinition addDefinitions(ReuestDefinition reuestDefinition) {
        this.definitions.add(reuestDefinition);
        reuestDefinition.setFieldDefinition(this);
        return this;
    }

    public FieldDefinition removeDefinitions(ReuestDefinition reuestDefinition) {
        this.definitions.remove(reuestDefinition);
        reuestDefinition.setFieldDefinition(null);
        return this;
    }

    public void setDefinitions(Set<ReuestDefinition> reuestDefinitions) {
        this.definitions = reuestDefinitions;
    }

    public DefinitionConfig getDefinition() {
        return definition;
    }

    public FieldDefinition definition(DefinitionConfig definitionConfig) {
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
        FieldDefinition fieldDefinition = (FieldDefinition) o;
        if (fieldDefinition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fieldDefinition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FieldDefinition{" +
            "id=" + getId() +
            ", fieldType='" + getFieldType() + "'" +
            ", key='" + getKey() + "'" +
            ", name='" + getName() + "'" +
            ", placeHolder='" + getPlaceHolder() + "'" +
            ", mandatory='" + isMandatory() + "'" +
            "}";
    }
}
