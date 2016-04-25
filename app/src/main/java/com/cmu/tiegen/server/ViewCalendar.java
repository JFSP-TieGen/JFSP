package server;

import java.util.Date;

import entity.CalendarDay;
import entity.User;

public interface ViewCalendar {
	public CalendarDay LoadCalendarDay(User user, Date date);
}
