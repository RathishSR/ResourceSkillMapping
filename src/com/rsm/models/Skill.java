package com.rsm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "skills")
public class Skill {
	private String skillId;
	private String skillName;
	private String skillType;
	private String skillDomain;
	
	@Id
	@GeneratedValue
	@Column(name = "skill_id")
	public String getSkillId() {
		return skillId;
	}
	
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	
	@Column(name = "name")
	public String getSkillName() {
		return skillName;
	}
	
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	
	public String getSkillType() {
		return skillType;
	}
	
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
	
	public String getSkillDomain() {
		return skillDomain;
	}
	
	public void setSkillDomain(String skillDomain) {
		this.skillDomain = skillDomain;
	}

}
