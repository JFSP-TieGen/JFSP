package server;

import java.util.ArrayList;

import entity.Booking;
import entity.Rate;
import entity.Service;

public interface RateOrder {
	public void rateOrder(Booking order);
	public void editRate(Booking order);
	public ArrayList<Rate> displayRates(Service service);
}
