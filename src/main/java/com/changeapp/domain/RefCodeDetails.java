package com.changeapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RefCodeDetails.
 */
@Entity
@Table(name = "ref_code_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RefCodeDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "ref_code")
    private String refCode;

    @Column(name = "ref_value")
    private String refValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public RefCodeDetails category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRefCode() {
        return refCode;
    }

    public RefCodeDetails refCode(String refCode) {
        this.refCode = refCode;
        return this;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getRefValue() {
        return refValue;
    }

    public RefCodeDetails refValue(String refValue) {
        this.refValue = refValue;
        return this;
    }

    public void setRefValue(String refValue) {
        this.refValue = refValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefCodeDetails refCodeDetails = (RefCodeDetails) o;
        if (refCodeDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refCodeDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefCodeDetails{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", refCode='" + getRefCode() + "'" +
            ", refValue='" + getRefValue() + "'" +
            "}";
    }
}
