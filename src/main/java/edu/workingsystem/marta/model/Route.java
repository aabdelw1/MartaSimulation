package edu.workingsystem.marta.model;

import java.util.ArrayList;
import java.util.List;

public class Route {
	public Integer routeId;
	public Integer routeNumber;
	public String routeName;
	public List<Integer> stopsList=new ArrayList<Integer>();
	
	public Integer getRouteId() {
		return routeId;
	}
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public List<Integer> getStopsList() {
		return stopsList;
	}
	public void setStopsList(List<Integer> stopsList) {
		this.stopsList = stopsList;
	}
	public void addStop(Integer stopId) {
		this.stopsList.add(stopId);
	}
	public void removeStop(Integer stopId) {
		this.stopsList.remove(stopId);
	}
	
	public Integer getNextStop(Integer location) {
		if(location<this.stopsList.size()-1) {
			return this.stopsList.get(location+1);
		}else {
			return this.stopsList.get(0);
			
		}
		
	}
	public Integer getCurrentStop(Integer location) {
		if(location<this.stopsList.size()) 
			return this.stopsList.get(location);
		return this.stopsList.get(0);
	}
	public Integer getRouteNumber() {
		return routeNumber;
	}
	public void setRouteNumber(Integer routeNumber) {
		this.routeNumber = routeNumber;
	}
	public Route(Integer routeId, Integer routeNumber, String routeName, List<Integer> stopsList) {
		super();
		this.routeId = routeId;
		this.routeNumber = routeNumber;
		this.routeName = routeName;
		this.stopsList = stopsList;
	}
	public Route(Integer routeId, Integer routeNumber, String routeName) {
		super();
		this.routeId = routeId;
		this.routeNumber = routeNumber;
		this.routeName = routeName;
		
	}
	
	

}
