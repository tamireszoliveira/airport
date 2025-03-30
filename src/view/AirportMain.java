package view;

import controller.Airport;

public class AirportMain {

	public static void main(String[] args) {
		Airport airport = new Airport(0, null, null);
		airport.takeoff();
	}

}
