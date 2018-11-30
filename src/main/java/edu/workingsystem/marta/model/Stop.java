package edu.workingsystem.marta.model;

import java.util.HashMap;

import edu.workingsystem.marta.util.RandomGeneratorUtil;

public class Stop {
	private Integer stopId;
	private String stopName;
	private Integer numberOfPassengers;
	private Double lat;
	private Double lon;
	private Integer ridersArriveHigh;
	private Integer ridersArriveLow;
	private Integer ridersOffHigh;
	private Integer ridersOffLow;
	private Integer ridersOnHigh;
	private Integer ridersOnLow;


	private Integer waiting;
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
		return lon;
	}
	public void setLongi(Double longi) {
		this.lon = longi;
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

	public Integer getRidersArriveHigh() {
		return ridersArriveHigh;
	}

	public void setRidersArriveHigh(Integer ridersArriveHigh) {
		this.ridersArriveHigh = ridersArriveHigh;
	}

	public Integer getRidersArriveLow() {
		return ridersArriveLow;
	}

	public void setRidersArriveLow(Integer ridersArriveLow) {
		this.ridersArriveLow = ridersArriveLow;
	}

	public Integer getRidersOffHigh() {
		return ridersOffHigh;
	}

	public void setRidersOffHigh(Integer ridersOffHigh) {
		this.ridersOffHigh = ridersOffHigh;
	}

	public Integer getRidersOffLow() {
		return ridersOffLow;
	}

	public void setRidersOffLow(Integer ridersOffLow) {
		this.ridersOffLow = ridersOffLow;
	}

	public Integer getRidersOnHigh() {
		return ridersOnHigh;
	}

	public void setRidersOnHigh(Integer ridersOnHigh) {
		this.ridersOnHigh = ridersOnHigh;
	}

	public Integer getRidersOnLow() {
		return ridersOnLow;
	}

	public void setRidersOnLow(Integer ridersOnLow) {
		this.ridersOnLow = ridersOnLow;
	}

	public Stop(Integer stopId, String stopName, Integer numberOfPassengers, Double lat, Double longi) {
		super();
		this.stopId = stopId;
		this.stopName = stopName;
		this.numberOfPassengers = numberOfPassengers;
		this.lat = lat;
		this.lon = longi;
		this.waiting =numberOfPassengers ;

	}

	public Double findDistance(Stop destination) {
		double distanceBetweenStops=Double.valueOf(
				70.0D * Math.sqrt(Math.pow(this.lat.doubleValue() - destination.getLat().doubleValue(), 2.0D)
						+ Math.pow(this.lon.doubleValue() - destination.getLongi().doubleValue(), 2.0D)));
		return distanceBetweenStops;
	}

	public Integer exchangeRiders(int rank, int initialPassengerCount, int capacity) {

		//Get the random number to get the passenger leave from the bus
		int leavingBus = RandomGeneratorUtil.getRandomRidersOff(this.ridersOffLow, this.ridersOffHigh);
		int updatedPassengerCount = Math.max(0, initialPassengerCount - leavingBus);
		int catchingBus =  RandomGeneratorUtil.getRandomRidersOn(this.ridersOnLow, this.ridersOnHigh);

		this.waiting = RandomGeneratorUtil.getRandomRidersArrive(this.ridersArriveLow, this.ridersArriveHigh);

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
