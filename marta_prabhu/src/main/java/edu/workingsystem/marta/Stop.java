package edu.workingsystem.marta;

import java.util.HashMap;

import edu.workingsystem.marta.util.RandomGeneratorUtil;

public class Stop {
	public Integer stopId;
	public String stopName;
	public Integer numberOfPassengers;
	public Double lat;
	public Double longi;
	private Integer waiting=RandomGeneratorUtil.getRandomRidersArrive();
	private Integer transfer;
	 private HashMap<Integer, int[]> boardingBusrate;
	 private HashMap<Integer, int[]> passengerLeaveRate;
	
	
	public Integer getStopId() {
		return stopId;
	}
	public void setStopId(Integer stopId) {
		this.stopId = stopId;
	}
	public String getStopName() {
		return stopName;
	}
	public void setStopName(String stopName) {
		this.stopName = stopName;
	}
	public Integer getNumberOfPassengers() {
		return numberOfPassengers;
	}
	public void setNumberOfPassengers(Integer numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLongi() {
		return longi;
	}
	public void setLongi(Double longi) {
		this.longi = longi;
	}
	
	public Integer getWaiting() {
		return waiting;
	}
	public void setWaiting(Integer waiting) {
		this.waiting = waiting;
	}
	public Integer getTransfer() {
		return transfer;
	}
	public void setTransfer(Integer transfer) {
		this.transfer = transfer;
	}
	public Stop(Integer stopId, String stopName, Integer numberOfPassengers, Double lat, Double longi) {
		super();
		this.stopId = stopId;
		this.stopName = stopName;
		this.numberOfPassengers = numberOfPassengers;
		this.lat = lat;
		this.longi = longi;
		this.waiting =numberOfPassengers ;
		
	}

	public Double findDistance(Stop destination) {
		double distanceBetweenStops=Double.valueOf(
				70.0D * Math.sqrt(Math.pow(this.lat.doubleValue() - destination.getLat().doubleValue(), 2.0D)
						+ Math.pow(this.longi.doubleValue() - destination.getLongi().doubleValue(), 2.0D)));
		return distanceBetweenStops;
	}
	
	public Integer exchangeRiders(int rank, int initialPassengerCount, int capacity) {
		
		//Get the random number to get the passenger leave from the bus
		int leavingBus = RandomGeneratorUtil.getRandomRidersOff();
		int updatedPassengerCount = Math.max(0, initialPassengerCount - leavingBus);
		int catchingBus =  RandomGeneratorUtil.getRandomRidersOn();

		int tryingToBoard = this.waiting.intValue() + catchingBus;
		int availableSeats = capacity - updatedPassengerCount;
		int ableToBoard;
		if (tryingToBoard > availableSeats) {
			ableToBoard = availableSeats;
			this.waiting = Integer.valueOf(tryingToBoard - availableSeats);
		} else {
			ableToBoard = tryingToBoard;
			this.waiting = Integer.valueOf(0);
		}

		int finalPassengerCount = updatedPassengerCount + ableToBoard;
		return Integer.valueOf(finalPassengerCount - initialPassengerCount);
	}
	
}
