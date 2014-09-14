package com.ggl.cache;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Employee implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private long id;
	
	private String name;
	
	private String address;
	
	public Employee(long id, String name, String address) 
	{
		this.id = id;
		this.name = name;
		this.address = address;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Name", this.getName()).append("Address", this.getAddress()).toString();
	}
}
