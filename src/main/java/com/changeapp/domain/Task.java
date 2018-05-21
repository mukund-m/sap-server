package com.changeapp.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {

	Long id;
	String name;
	LocalDate dueDate;
	LocalDate completedDate;
	LocalDate notifedDate;
	Request request;
	String instruction;
	Long configId;
	List<TaskStructureConfig> question;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public LocalDate getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(LocalDate completedDate) {
		this.completedDate = completedDate;
	}
	public LocalDate getNotifedDate() {
		return notifedDate;
	}
	public void setNotifedDate(LocalDate notifedDate) {
		this.notifedDate = notifedDate;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<TaskStructureConfig> getQuestion() {
		return question;
	}
	public void setQuestion(List<TaskStructureConfig> question) {
		this.question = question;
	}
	public Long getConfigId() {
		return configId;
	}
	public void setConfigId(Long configId) {
		this.configId = configId;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	
	
}
