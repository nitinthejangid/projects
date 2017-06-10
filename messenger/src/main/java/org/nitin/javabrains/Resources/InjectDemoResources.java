package org.nitin.javabrains.Resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResources {

	@GET
	@Path("/annotations")
	@Consumes(MediaType.TEXT_PLAIN)
	public String getParamUsingAnnotation(@MatrixParam("param") String metrixparam,
			@HeaderParam("authCredential") String authCredential,
			@HeaderParam("password") String password,
			@CookieParam("JSESSIONID") String cookie ){
		System.out.println("Hello");
		
		return "Test " + "MetrixParam " + metrixparam + "AuthCredential " + authCredential + " password " + password 
				+ " cookieParam " + cookie;
		
	}
	@GET
	@Path("/context")
	
	public String getContextParameter(@Context UriInfo uriinfo, @Context HttpHeaders header)
	{
		String uri = uriinfo.getAbsolutePath().toString();
		String getRequestUri = uriinfo.getRequestUri().toString();
		
		String headers = header.getCookies().toString();
		
		return " URI: " + uri + " getRequestUri " + getRequestUri + " headers: " + headers;
		
	}
}
