package com.cmu.tiegen.entity;

import java.io.Serializable;

public class Service implements Serializable{
    	private static final long serialVersionUID = 6529685098267757690L;

	int serviceId;
	String name;
	String type;
	String location;
	String link;
	//record the average rate of this service in database
	float avgRate;
	float price;

	public Service(String name, String type, String location, String link, float avgRate, float price) {
		super();
		this.name = name;
		this.type = type;
		this.location = location;
		this.link = link;
		this.avgRate = avgRate;
		this.price = price;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public float getAvgRate() {
		return avgRate;
	}
	public void setAvgRate(float avgRate) {
		this.avgRate = avgRate;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	

	
}
