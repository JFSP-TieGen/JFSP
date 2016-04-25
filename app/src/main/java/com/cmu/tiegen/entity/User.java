package entity;

import java.util.ArrayList;

public class User {
	
	int userId;
	String userName;
	String password;
	int gender;
	int age;
	/*when user logged in, get everything include all the book marks from database and
	 * encapsulate them here in this Arraylist
	 */
	ArrayList<Service> bookmarks;
	/*when user logged in, get everything include all the Calendardays from database and
	 * encapsulate them here in this Arraylist
	 */
	ArrayList<CalendarDay> CalendarDays;

}
