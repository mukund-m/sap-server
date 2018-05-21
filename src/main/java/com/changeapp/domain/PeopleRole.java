package com.changeapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PeopleRole.
 */
@Entity
@Table(name = "people_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PeopleRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne
    private RequestTypeDefConfig requestTypeDefConfig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public PeopleRole roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public RequestTypeDefConfig getRequestTypeDefConfig() {
        return requestTypeDefConfig;
    }

    public PeopleRole requestTypeDefConfig(RequestTypeDefConfig requestTypeDefConfig) {
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
        PeopleRole peopleRole = (PeopleRole) o;
        if (peopleRole.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), peopleRole.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeopleRole{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            "}";
    }
}
