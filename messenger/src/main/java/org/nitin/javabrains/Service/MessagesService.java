package org.nitin.javabrains.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nitin.javabrains.Database.DatabaseClass;
import org.nitin.javabrains.Model.Messages;
import org.nitin.javabrains.exception.DataNotFoundException;

public class MessagesService {

	private static Map<Long, Messages> messages = DatabaseClass.getMessages(); 
	

	public MessagesService()
	{

		messages.put(1L, new Messages(1,"Hello Nitin","Nitin"));
		messages.put(2L, new Messages(2,"Hello Ravi","Ravi"));
		messages.put(3L, new Messages(3,"Hello Mahi","Mahi"));
	}
	
	public List<Messages> getAllMessages()
	{
		return new ArrayList<Messages>(messages.values());
	}
	
	public Messages getSecondMessages(Long id)
	{
		return messages.get(id);
	}
	
	public static Messages updateMessage(Messages message)
	{
		if(message.getId()<=0)
		{
			return null;
	
		}
		else
		{
			
			return messages.put((Long)message.getId(),message);
		}
	}
	public Messages getMessage(long id)
	{
		Messages message = messages.get(id);
		if(message==null)
		{
			throw new DataNotFoundException("Message with id " + id + " not found");
		}
		return messages.get(id);
	}
	
	public Messages createMessage(Messages message)
	{
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Messages removeMessage(long id)
	{
		return messages.remove(id);
	}
}

