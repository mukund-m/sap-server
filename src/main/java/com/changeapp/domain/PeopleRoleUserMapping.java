package com.changeapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PeopleRoleUserMapping.
 */
@Entity
@Table(name = "people_role_user_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PeopleRoleUserMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userID;

    @ManyToOne
    private RequestTypeDefConfig requestTypeDefConfig;

    @ManyToOne
    private PeopleRole peopleRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public PeopleRoleUserMapping userID(String userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public RequestTypeDefConfig getRequestTypeDefConfig() {
        return requestTypeDefConfig;
    }

    public PeopleRoleUserMapping requestTypeDefConfig(RequestTypeDefConfig requestTypeDefConfig) {
        this.requestTypeDefConfig = requestTypeDefConfig;
        return this;
    }

    public void setRequestTypeDefConfig(RequestTypeDefConfig requestTypeDefConfig) {
        this.requestTypeDefConfig = requestTypeDefConfig;
    }

    public PeopleRole getPeopleRole() {
        return peopleRole;
    }

    public PeopleRoleUserMapping peopleRole(PeopleRole peopleRole) {
        this.peopleRole = peopleRole;
        return this;
    }

    public void setPeopleRole(PeopleRole peopleRole) {
        this.peopleRole = peopleRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PeopleRoleUserMapping peopleRoleUserMapping = (PeopleRoleUserMapping) o;
        if (peopleRoleUserMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), peopleRoleUserMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeopleRoleUserMapping{" +
            "id=" + getId() +
            ", userID='" + getUserID() + "'" +
            "}";
    }
}
