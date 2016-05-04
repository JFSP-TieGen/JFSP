package com.cmu.tiegen.entity;

import java.io.Serializable;

public class BookMark implements Serializable{
private static final long serialVersionUID = 6529685098267757690L;

int serviceId;
int userId;
public BookMark(int sid, int uid){
	this.serviceId = sid;
	this.userId = uid;
}
public int getServiceId() {
	return serviceId;
}
public void setServiceId(int serviceId) {
	this.serviceId = serviceId;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}

}
