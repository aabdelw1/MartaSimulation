package edu.workingsystem.marta;

import java.util.ArrayList;
import java.util.HashMap;

public class MassTransitSystem {
	
	  private HashMap<Integer, Stop> stopsMap=new HashMap<Integer, Stop>();;
	  private HashMap<Integer, Route> routesMap=new HashMap<Integer, Route>();;
	  private HashMap<Integer, Bus> busMap=new HashMap<Integer, Bus>();;
	  private Integer depotID;
	  
	public HashMap<Integer, Stop> getStops() {
		return stopsMap;
	}
	public void setStops(HashMap<Integer, Stop> stopsMap) {
		this.stopsMap = stopsMap;
	}
	public HashMap<Integer, Route> getRoutes() {
		return routesMap;
	}
	public void setRoutes(HashMap<Integer, Route> routesMap) {
		this.routesMap = routesMap;
	}
	public HashMap<Integer, Bus> getBuses() {
		return busMap;
	}
	public void setBuses(HashMap<Integer, Bus> busMap) {
		this.busMap = busMap;
	}
	public Integer getDepotID() {
		return depotID;
	}
	public void setDepotID(Integer depotID) {
		this.depotID = depotID;
	}
	  
	public void addStop(Integer stopID, String stopName, Integer passengerCount, Double lati, Double longi) {
		Stop stop=new Stop(stopID,stopName,passengerCount,lati,longi);
		this.stopsMap.put(stopID, stop);
		
		
	}
	
	public void addRoute(Integer routeId,Integer routeNumber,String routeName) {
		Route route=new Route(routeId,routeNumber,routeName);
		this.routesMap.put(routeId, route);
	
	}
	
	public void addBus(Integer busID, Integer routeID, Integer currentLocation, Integer currentPassengerCount, Integer maxPassengerCount, Integer currentFuel, Integer fuelCapacity, Integer speed) {
		Bus bus=new Bus();
		bus.setBusId(busID);
		bus.setCurrentLocation(currentLocation);
		bus.setCurrentPassengerCount(currentPassengerCount);
		bus.setMaxPassengerCount(maxPassengerCount);
		bus.setCurrentFuel(currentFuel);
		bus.setFuelCapacity(fuelCapacity);
		bus.setSpeed(speed);
		bus.setRouteId(routeID);
		this.busMap.put(busID, bus);
		
	}
	
	public void addStopToRoute(Integer routeId,Integer stopId) {
		Route route=routesMap.get(routeId);
		ArrayList<Integer> routeStops=(ArrayList<Integer>) route.getStopsList();
		routeStops.add(stopId);
		route.setStopsList(routeStops);
		this.routesMap.put(routeId, route);
		
		
	}
	  
	  
	
	

}
