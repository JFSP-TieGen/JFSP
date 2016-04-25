package server;

import java.util.ArrayList;

import entity.Booking;
import entity.QueryInfo;
import entity.Service;
import entity.User;

public interface BookService {
	public void bookService(User user, Service service);
	public ArrayList<Service> loadAllServices();
	public void cancelOrder(Booking order);
	public ArrayList<Service> search(QueryInfo info);
	
	
}
