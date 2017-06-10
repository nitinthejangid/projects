package org.nitin.javabrains.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class CommentsResources {


	@GET
	public String getComments()            
	{
		return "Test1";
	}
	
	@GET
	@Path("/{commentsId}")
	public String test(@PathParam("commentsId") long cid, @PathParam("messageId") long mid  )
	{
		return " MessageId: " + mid + " commentsId:  " + cid;
		
	}
}
