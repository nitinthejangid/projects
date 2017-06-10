package org.nitin.javabrains.Resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


import javax.ws.rs.core.MediaType;

import org.nitin.javabrains.Model.Profiles;
import org.nitin.javabrains.Service.ProfilesService;

@Path("/profiles")
public class ProfilesResources {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public List<Profiles> getProfiles()
	{
		System.out.println("Get resorces method is called!!!");
		return new ProfilesService().getAllProfiles();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Profiles getSingleProfile(String profileName)
	{
		return new ProfilesService().getProfile(profileName);
	}
	
	@POST
	@Path("/profileName")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Profiles createProfiles(Profiles profile, @PathParam("profileName") String profileName)
	{
		return new ProfilesService().createProfiles(profile, profileName);
	}
	
	
}

