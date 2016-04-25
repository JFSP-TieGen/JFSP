package server;

import java.util.ArrayList;

import entity.Service;
import entity.User;

public interface BookMark {
	public void addBookMark(User user, Service service);
	public ArrayList<Service> loadBookMark(User user);
	public void deleteBookMark(User user, Service service);
}
