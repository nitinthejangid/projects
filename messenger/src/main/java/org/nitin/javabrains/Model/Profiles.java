package org.nitin.javabrains.Model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Profiles {

	public Profiles(long id, String profileName, String firstName,
			String lastName) {

		this.id = id;
		this.profileName = profileName;
		this.firstName = firstName;
		this.lastName = lastName;

	}
	private long id;
	private String profileName;
	private String firstName;
	private String lastName;
	private Date date;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
