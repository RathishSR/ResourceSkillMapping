package com.rsm.dao.interfaces;

import java.util.List;

import com.rsm.dto.TaskResourcesDto;
import com.rsm.models.Task;

public interface ResourceMatchingDao {
	public List<Task> getAllTasksForProject(String projectId);
	public List<TaskResourcesDto> getListOfResources(String projectId);
}
