package com.rsm.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "resources")
public class Resource {
	private String resourceId;
	private String resourceName;
	private Set<Skill> skills;
	private Set<Task> tasks;
	
	@Id
	@GeneratedValue
	@Column(name = "resource_id")
	public String getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@Column(name = "name")
	public String getResourceName() {
		return resourceName;
	}
	
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	@ManyToMany(targetEntity = Skill.class, cascade = CascadeType.ALL)
	@JoinTable(name = "resources_skills",
			joinColumns = { @JoinColumn(name = "resource_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "skill_id") }
	)
	public Set<Skill> getSkills() {
		return skills;
	}
	
	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	@ManyToMany(targetEntity = Task.class, cascade = CascadeType.ALL)
	@JoinTable(name = "tasks_resources",
			joinColumns = { @JoinColumn(name = "resource_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "task_id") }
	)
	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	
}
