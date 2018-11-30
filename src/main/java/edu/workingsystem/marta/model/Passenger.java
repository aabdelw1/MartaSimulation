package edu.workingsystem.marta.model;

public class Passenger {
	
	private Integer passengerId;
	private String name;
	private Integer startStopId;
	private Integer endStopId;
	private Integer busId;
	
	public Integer getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(Integer passengerId) {
		this.passengerId = passengerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStartStopId() {
		return startStopId;
	}
	public void setStartStopId(Integer startStopId) {
		this.startStopId = startStopId;
	}
	public Integer getEndStopId() {
		return endStopId;
	}
	public void setEndStopId(Integer endStopId) {
		this.endStopId = endStopId;
	}
	public Integer getBusId() {
		return busId;
	}
	public void setBusId(Integer busId) {
		this.busId = busId;
	}
	
	

}
