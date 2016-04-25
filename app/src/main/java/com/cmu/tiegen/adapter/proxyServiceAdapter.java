package adapter;

import java.util.ArrayList;
import java.util.Date;

import entity.CalendarDay;
import entity.Booking;
import entity.QueryInfo;
import entity.Rate;
import entity.Service;
import entity.User;

public abstract class proxyServiceAdapter {
	public void bookService(User user,Service service){
	}
	public void addBookMark(User user,Service service){
		
	}
	public CalendarDay loadCalendarDay(User user, Date date){
		return null;
	}
	public void rateOrder(Booking order){
		
	}
	public ArrayList<Service> loadAllServices(){
		return null;
	}
	public ArrayList<Service> loadBookMarks(User user){
		return null;
	}
	public void editRate(Booking order){
		
	}
	public void cancelOrder(Booking order){
		
	}
	public void deleteBookmark(User user,Service service){
		
	}
	public ArrayList<Service> search(QueryInfo qinfo){
		return null;
	}
	public ArrayList<Rate> displayReviews(Service service){
		return  null;
	}
}
