package com.rsm.dto;

import java.util.List;

import com.rsm.models.Resource;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskResourcesDto {

	private List<Resource> resources;
	private String taskId;
	private String taskName;
	
	public TaskResourcesDto(List<Resource> resources, String taskId, String taskName) {
		this.resources = resources;
		this.taskId = taskId;
		this.taskName = taskName;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
