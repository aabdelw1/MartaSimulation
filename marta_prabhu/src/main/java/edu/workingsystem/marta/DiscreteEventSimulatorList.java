package edu.workingsystem.marta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DiscreteEventSimulatorList {
	
	static MassTransitSystem transitSystem=new MassTransitSystem();
	static Comparator comp=new  EventComparator();
	static List<Event> eventList=new ArrayList<Event>();
	static PriorityQueue<Event> eventQueue = new java.util.PriorityQueue(50, comp);

	public static void main(String[] args) {

		File file = new File(args[0]);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;

			while ((st = br.readLine()) != null) {
				
				String[] output = st.split("\\,");
				String event=output[0];
				
				if(null!=event) {
					
					if("add_depot".equals(event)) {
						Integer depotId=Integer.parseInt(output[1]);
						String stopName=output[2];
						Integer passengerCount=0;
						Double lati=Double.parseDouble(output[3]);
						Double longi=Double.parseDouble(output[4]);
						transitSystem.addStop(depotId,stopName,passengerCount,lati,longi);
						transitSystem.setDepotID(depotId);
						
					}
					
					else if("add_stop".equals(event)) {
						Integer stopId=Integer.parseInt(output[1]);
						String stopName=output[2];
						Integer passengerCount=Integer.parseInt(output[3]);
						Double lati=Double.parseDouble(output[4]);
						Double longi=Double.parseDouble(output[5]);
						transitSystem.addStop(stopId,stopName,passengerCount,lati,longi);
						
					}
					
					else if("add_route".equals(event)) {
						Integer routeId=Integer.parseInt(output[1]);
						Integer routeNumber=Integer.parseInt(output[2]);
						String routeName=output[3];
						transitSystem.addRoute(routeId, routeNumber, routeName);
					}
					else if("extend_route".equals(event)) {
						Integer routeId=Integer.parseInt(output[1]);
						Integer stopId=Integer.parseInt(output[2]);
						transitSystem.addStopToRoute(routeId, stopId);
					}
					
					else if("add_bus".equals(event)) {
						Integer busID= Integer.parseInt(output[1]);
						Integer routeID=Integer.parseInt(output[2]);
						Integer currentLocation=Integer.parseInt(output[3]);
						Integer currentPassengerCount=Integer.parseInt(output[4]);
						Integer maxPassengerCount=Integer.parseInt(output[5]);
						Integer currentFuel= Integer.parseInt(output[6]);
						Integer fuelCapacity= Integer.parseInt(output[7]);
						Integer speed=Integer.parseInt(output[8]);
						transitSystem.addBus(busID, routeID, currentLocation, currentPassengerCount, maxPassengerCount, currentFuel, fuelCapacity, speed);
					}
					
					else if("add_event".equals(event)) {
						Event event1=new Event(Integer.parseInt(output[1]),output[2],Integer.parseInt(output[3]));
						eventList.add(event1);
						Collections.sort(eventList, comp);
						
					}
					
				}else {
					System.out.println("Event cannot be null");
				}
				
			}
			// Determine which bus to move
			moveBus(transitSystem,eventList);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void moveBus(MassTransitSystem transitSystem, List<Event> eventList) {
		int count=0;
		if(null!=eventList) {
			for (int i=0;i<eventList.size();i++) {
				
				Event event=eventList.get(i);
				if (count < 20) {
					if ("move_bus".equals(event.getEvent_type())) {
						// Bus to be moved
						Integer busId = event.getEventId();
						Bus activeBus = transitSystem.getBuses().get(busId);
						// Route of the bus
						Integer routeId = activeBus.getRouteId();
						Route busRoute = transitSystem.getRoutes().get(routeId);
						// Current stop of bus

						Integer currentLocation = activeBus.getCurrentLocation();
						Integer currentStopId = busRoute.getCurrentStop(currentLocation);

						// Next stop of the bus
						Integer nextStopId = busRoute.getNextStop(currentLocation);
						Stop currentStop = transitSystem.getStops().get(currentStopId);
						Stop nextStop = transitSystem.getStops().get(nextStopId);

						// Distance

						Double pathDistance = currentStop.findDistance(nextStop);
						int travelTime = 1 + pathDistance.intValue() * 60 / activeBus.getSpeed().intValue();
						//currentLocation=busRoute.getStopsList().indexOf(currentStop);
						if(currentLocation<busRoute.getStopsList().size()-1) {
						activeBus.setCurrentLocation(currentLocation+1);
						}else {
							activeBus.setCurrentLocation(0);
						}

						// Arrival time
						Integer nextArrivalTime = Integer.valueOf(event.getEventRank().intValue() + travelTime);
						activeBus.setArrivalTime(nextArrivalTime.intValue());
						eventList.remove(event);
						transitSystem.getBuses().remove(busId);
						transitSystem.getBuses().put(busId, activeBus);
						i--;
						eventList.add(new Event(nextArrivalTime.intValue(), "move_bus", event.getEventId().intValue()));
						System.out.println("b:" + activeBus.getBusId() + "->s:" + nextStop.getStopId() + "@"+ activeBus.getArrivalTime() + "//p:0/f:0");
						Collections.sort(eventList, comp);
						count++;

					}

				}else {
					break;
				}
			}
			
		}
		

	         
	}

}

