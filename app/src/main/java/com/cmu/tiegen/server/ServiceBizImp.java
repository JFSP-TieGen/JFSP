package server;

import java.util.ArrayList;
import java.util.Date;

import entity.CalendarDay;
import entity.Rate;
import entity.Service;
import entity.User;
import adapter.proxyServiceAdapter;

public class ServiceBizImp extends proxyServiceAdapter implements BookMark,BookService,RateOrder,ViewCalendar{

	@Override
	public CalendarDay LoadCalendarDay(User user, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Rate> displayRates(Service service) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Service> loadBookMark(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBookMark(User user, Service service) {
		// TODO Auto-generated method stub
		
	}

}
