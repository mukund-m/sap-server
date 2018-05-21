package com.changeapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TreeNode implements Serializable {

	String name;
	String type;
	Long id;
	String status;
	String resp;
	LocalDate dueDate;
	LocalDate notifiedDate;
	LocalDate completedDate;
	List<TreeNode> children = new ArrayList<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long long1) {
		this.id = long1;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResp() {
		return resp;
	}
	public void setResp(String resp) {
		this.resp = resp;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public LocalDate getNotifiedDate() {
		return notifiedDate;
	}
	public void setNotifiedDate(LocalDate notifiedDate) {
		this.notifiedDate = notifiedDate;
	}
	public LocalDate getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(LocalDate completedDate) {
		this.completedDate = completedDate;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	
	
}
