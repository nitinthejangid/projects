package org.nitin.javabrains.Model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement

public class Messages {

	private long id;
	private String message;
	private Date date;
	private String author;
	
	public Messages()
	{}
	
	public Messages(long id, String message, String author) {
		super();
		this.id = id;
		this.message = message;

		this.author = author;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
