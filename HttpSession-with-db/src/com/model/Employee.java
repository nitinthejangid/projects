package com.model;

public class Employee {

	private Object _id;
	private String name;
	private String email;
	private String mobile;
	private String password;
	
	public Employee(String name, String email, String mobile, String password,Object _id) {

		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this._id=_id;
	}
	
	public Employee(String name, String email, String mobile, String password) {

		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Object get_id() {
		return _id;
	}

	public void set_id(Object _id) {
		this._id = _id;
	}
	
	
}
