package org.nitin.javabrains.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nitin.javabrains.Database.DatabaseClass;
import org.nitin.javabrains.Model.Profiles;

public class ProfilesService {

	Map<String, Profiles> dProfiles = DatabaseClass.getProfiles();
	
	public ProfilesService()
	{
		System.out.println("Custractor called!!!");
		dProfiles.put("Nitin", new Profiles(1,"Nitin", "Nitin","Jangid"));
		dProfiles.put("Ravi", new Profiles(2,"Ravi", "Ravi","Jangid"));
		dProfiles.put("Nandha", new Profiles(3,"Nandha", "Nandha","Jangid"));
	}
	
	public List<Profiles> getAllProfiles()
	{
		System.out.println("Get method called!!!");
		List<Profiles> profiles = new ArrayList<>(dProfiles.values());
		Profiles profile1 = profiles.get(0);
		System.out.println(profile1.getProfileName());
		return profiles;
	}
	
	public Profiles getProfile(String profileName)
	{
		return dProfiles.get(profileName);
		
	}
	
	public Profiles deleteProfile(String profileName)
	{
		
		return dProfiles.remove(profileName);
		
	}
	
	public Profiles createProfiles(Profiles profile,String profileName)
	{
		profile.setId(dProfiles.size()+1);
		profile.setProfileName(profileName);
		return dProfiles.put(profile.getProfileName(), profile);
	}
	
	public Profiles updateProfiles(String profileName, Profiles profile)
	{
		profile.setProfileName(profileName);
		return dProfiles.put(profileName, profile);
	}
	
}

