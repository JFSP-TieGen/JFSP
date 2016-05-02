package com.cmu.tiegen.entity;

//used when display services in different types and searh for services
public class QueryInfo {
    	private static final long serialVersionUID = 6529685098267757690L;

	String serviceName;
	String location;
	String type;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
