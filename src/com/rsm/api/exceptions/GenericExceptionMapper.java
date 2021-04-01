package com.rsm.api.exceptions;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		if(exception instanceof DataNotFoundException) {
			return Response.status(Response.Status.NOT_FOUND)
				       .entity("{\"error\" : \"No result found for the given project Id\"}")
				       .build();
		} else if (exception instanceof DBException){
			String responseString = "{\"error\" : \"DB exception occurred.\"}";
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(responseString)
					.build();
		} else {
			String responseString = "{\"error\" : \"Some server exception occurred."
					+exception.getStackTrace().toString()+"\"}";
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(responseString)
					.build();
		}
	}

}
