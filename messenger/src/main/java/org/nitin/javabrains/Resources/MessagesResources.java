package org.nitin.javabrains.Resources;


import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.nitin.javabrains.Model.Messages;
import org.nitin.javabrains.Service.MessagesService;

@Path("messages")
public class MessagesResources {

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Messages> getMessages(@QueryParam("id") long id)
	{
		if(id==2)
		{
			return (List<Messages>) new MessagesService().getSecondMessages(id);
		}
		return new MessagesService().getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Messages getMessage(@PathParam("messageId") long id)
	{
		
		return new MessagesService().getMessage(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewMessage(Messages message, @Context UriInfo uriInfo)
	{
		Messages message1 = new MessagesService().createMessage(message);
		String pathid = String.valueOf(message1.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(pathid).build();
		return Response.created(uri).entity(message).build();
		
	}
	
	@DELETE
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Messages deleteMessage(@PathParam("messageId") long id)
	{
		return new MessagesService().removeMessage(id);
	}
	
	@PUT
	@Path("/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Messages updateNewMessage(Messages message, @PathParam("messageId") long id)
	{
		message.setId(id);
		return MessagesService.updateMessage(message);
	}
	

	@Path("/{messageId}/comments")
	public CommentsResources getComments()
	{
		return new CommentsResources();
	}
	
}
