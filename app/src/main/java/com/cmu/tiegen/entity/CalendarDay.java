package com.cmu.tiegen.entity;

import java.util.ArrayList;
import java.util.Date;

// user's schedule in one calendar day
public class CalendarDay {
    	private static final long serialVersionUID = 6529685098267757690L;

	int userId;
	Date date;
	ArrayList<Booking> orders;
}
