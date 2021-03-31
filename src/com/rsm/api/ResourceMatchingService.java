package com.rsm.api;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.rsm.dao.ResourceMatchingDaoImpl;
import com.rsm.dao.interfaces.ResourceMatchingDao;
import com.rsm.dto.TaskResourcesDto;
import com.rsm.models.Resource;
import com.rsm.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/rsm")
public class ResourceMatchingService {
	
	
	@GET
	@Path("/getResourcesForProject")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TaskResourcesDto> getResourcesForProject(@QueryParam("projectId") String projectId) {
		System.out.println("Before null check");
		if (projectId == null) {
		    throw new WebApplicationException(
		      Response.status(Response.Status.BAD_REQUEST)
		        .entity("projectId is mandatory")
		        .build()
		    );
		}
		System.out.println("After null check");
		ResourceMatchingDao resMatchDao = new ResourceMatchingDaoImpl();
		List<TaskResourcesDto> result = resMatchDao.getListOfResources(projectId);
		return result;
	}
	
	@GET
	@Path("/dummyCall")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Resource> getDummyCall() {
		List<Resource> resources = new ArrayList<Resource>();
		Resource r = new Resource();
		r.setResourceId("1");
		r.setResourceName("Rathish");
		Resource r1 = new Resource();
		r.setResourceId("2");
		r.setResourceName("Mithun");
		Resource r2 = new Resource();
		r.setResourceId("3");
		r.setResourceName("Nirmal");
		resources.add(r);
		resources.add(r1);
		resources.add(r2);
		return resources;
	}
}
