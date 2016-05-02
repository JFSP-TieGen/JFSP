package com.cmu.tiegen.entity;

import java.util.Date;

//a booked service
public class Booking {	
    	private static final long serialVersionUID = 6529685098267757690L;

	private int orderId;
	private int userId;
	private int serviceId;
	private Date date;
	private Rate rate;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Rate getRate() {
		return rate;
	}
	public void setRate(Rate rate) {
		this.rate = rate;
	}
	
	

}
