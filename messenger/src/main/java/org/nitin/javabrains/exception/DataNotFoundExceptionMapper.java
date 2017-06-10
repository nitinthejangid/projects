package org.nitin.javabrains.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.nitin.javabrains.Model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		// TODO Auto-generated method stub
		System.out.println("Mapper is called");
		ErrorMessage error = new ErrorMessage(ex.getMessage(), 404, "www.google.com");
		return Response.status(Status.NOT_FOUND)
				.entity(error)
				.build();
	}

}
