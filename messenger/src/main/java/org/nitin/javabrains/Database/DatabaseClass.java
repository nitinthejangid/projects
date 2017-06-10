package org.nitin.javabrains.Database;

import java.util.HashMap;
import java.util.Map;

import org.nitin.javabrains.Model.Messages;
import org.nitin.javabrains.Model.Profiles;

public class DatabaseClass {

	private static Map<Long, Messages> messages = new HashMap<>();
	private static Map<String, Profiles> Profiles = new HashMap<>();
	
	public static Map<Long, Messages> getMessages()
	{
		return messages;
	}
	
	public static Map<String, Profiles> getProfiles()
	{
		return Profiles;
	}
}
