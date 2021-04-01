package com.rsm.api;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.rsm.api.exceptions.DataNotFoundException;
import com.rsm.api.exceptions.GenericExceptionMapper;
import com.rsm.api.exceptions.InvalidProjectIdException;
import com.rsm.dao.ResourceMatchingDaoImpl;
import com.rsm.dao.interfaces.ResourceMatchingDao;
import com.rsm.dto.TaskResourcesDto;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/v1")
public class ResourceMatchingService {
	
	private final static Logger LOGGER = 
             Logger.getLogger(ResourceMatchingService.class.getName());

	@GET
	@Path("/getResourcesForProject")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResourcesForProject(@QueryParam("projectId") String projectId) {
		LOGGER.log(Level.INFO, "API call - getResourcesForProject");
		if (projectId == null || projectId.isEmpty()) {
			LOGGER.log(Level.SEVERE, "ProjectId is missing.");
		    throw new InvalidProjectIdException("id null");
		}
		ResourceMatchingDao resMatchDao;
		List<TaskResourcesDto> result = null;
		try {
			resMatchDao = new ResourceMatchingDaoImpl();
			result = resMatchDao.getListOfResources(projectId);
			if(result == null || result.isEmpty()) {
				throw new DataNotFoundException("Error");
			}
		} catch(DataNotFoundException ex) {
			LOGGER.log(Level.SEVERE, " Data missing for Project");
			return new GenericExceptionMapper().toResponse(ex);
		} catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "Error occurred");
			ex.printStackTrace();
			return new GenericExceptionMapper().toResponse(ex);
		}
		return Response.status(Response.Status.OK).entity(result).build();
	}
}
