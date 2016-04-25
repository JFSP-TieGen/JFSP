package entity;

import java.util.ArrayList;

public class Service {
	int serviceId;
	String name;
	int type;
	String location;
	String link;
	ArrayList<Rate> rates;
	//record the average rate of this service in database
	int avgRate;
}
