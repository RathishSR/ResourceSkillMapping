package com.rsm.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Rathish
 */
@Entity
@Table(name = "tasks")
public class Task {
	private String taskId;
	private Project project;
	private String taskName;
	private Skill skillRequired;
	private Date startDate;
	private Date endDate;
	
	@Id
	@GeneratedValue
	@Column(name = "task_id")
	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "project_id")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	@ManyToOne
	@JoinColumn(name = "skill_id")
	public Skill getSkillRequired() {
		return skillRequired;
	}
	
	public void setSkillRequired(Skill skillRequired) {
		this.skillRequired = skillRequired;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
