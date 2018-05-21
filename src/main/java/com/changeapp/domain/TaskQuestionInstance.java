package com.changeapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TaskQuestionInstance.
 */
@Entity
@Table(name = "task_question_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaskQuestionInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sort_in_parent_id")
    private String sortInParentID;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "notified_date")
    private LocalDate notifiedDate;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "question_response")
    private String questionResponse;

    @Column(name = "parent_id")
    private Long parentID;

    @Column(name = "definition_id")
    private Long definitionID;

    @ManyToOne
    private Request request;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSortInParentID() {
        return sortInParentID;
    }

    public TaskQuestionInstance sortInParentID(String sortInParentID) {
        this.sortInParentID = sortInParentID;
        return this;
    }

    public void setSortInParentID(String sortInParentID) {
        this.sortInParentID = sortInParentID;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskQuestionInstance dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getNotifiedDate() {
        return notifiedDate;
    }

    public TaskQuestionInstance notifiedDate(LocalDate notifiedDate) {
        this.notifiedDate = notifiedDate;
        return this;
    }

    public void setNotifiedDate(LocalDate notifiedDate) {
        this.notifiedDate = notifiedDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public TaskQuestionInstance completedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
        return this;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public String getStatus() {
        return status;
    }

    public TaskQuestionInstance status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuestionResponse() {
        return questionResponse;
    }

    public TaskQuestionInstance questionResponse(String questionResponse) {
        this.questionResponse = questionResponse;
        return this;
    }

    public void setQuestionResponse(String questionResponse) {
        this.questionResponse = questionResponse;
    }

    public Long getParentID() {
        return parentID;
    }

    public TaskQuestionInstance parentID(Long parentID) {
        this.parentID = parentID;
        return this;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }

    public Long getDefinitionID() {
        return definitionID;
    }

    public TaskQuestionInstance definitionID(Long definitionID) {
        this.definitionID = definitionID;
        return this;
    }

    public void setDefinitionID(Long definitionID) {
        this.definitionID = definitionID;
    }

    public Request getRequest() {
        return request;
    }

    public TaskQuestionInstance request(Request request) {
        this.request = request;
        return this;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskQuestionInstance taskQuestionInstance = (TaskQuestionInstance) o;
        if (taskQuestionInstance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskQuestionInstance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskQuestionInstance{" +
            "id=" + getId() +
            ", sortInParentID='" + getSortInParentID() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", notifiedDate='" + getNotifiedDate() + "'" +
            ", completedDate='" + getCompletedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", questionResponse='" + getQuestionResponse() + "'" +
            ", parentID='" + getParentID() + "'" +
            ", definitionID='" + getDefinitionID() + "'" +
            "}";
    }
}
