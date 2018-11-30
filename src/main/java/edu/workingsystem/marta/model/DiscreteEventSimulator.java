package edu.workingsystem.marta.model;

import edu.workingsystem.marta.util.EventComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class DiscreteEventSimulator {

    private MassTransitSystem transitSystem;
    private Comparator comp;
    private PriorityQueue<Event> eventQueue;

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscreteEventSimulator.class);

    public DiscreteEventSimulator(int capacity){
        this.transitSystem = new MassTransitSystem();
        this.comp = new EventComparator();
        this.eventQueue = new PriorityQueue(capacity, this.comp);
    }


    public SystemStateResponse startSim(String scenarioFilePath, String probabilityFilePath) {

        String result = "";

        File file = new File(scenarioFilePath);
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
                        eventQueue.add(event1);
                        //Collections.sort(eventList, comp);
                    }

                }else {
                    LOGGER.debug("Event can not be null");
                }

            }

        } catch (FileNotFoundException e) {
            LOGGER.error("Scenario file not found", e);
        } catch (IOException e) {
            LOGGER.error("Scenario file I/O exception", e);
        }

        File file2 = new File(probabilityFilePath);
        try {
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            String st;

            while ((st = br2.readLine()) != null) {

                String[] output = st.split("\\,");
                String stopid = output[0];

                HashMap<Integer,Stop> stops = this.transitSystem.getStops();

                if(null!=stopid) {
                    if (stops.containsKey(stopid)){
                        Stop stop = stops.get(stopid);
                        Integer ridersArriveHigh = Integer.parseInt(output[1]);
                        Integer ridersArriveLow = Integer.parseInt(output[2]);
                        Integer ridersOffHigh = Integer.parseInt(output[3]);
                        Integer ridersOffLow = Integer.parseInt(output[4]);
                        Integer ridersOnHigh = Integer.parseInt(output[5]);
                        Integer ridersOnLow = Integer.parseInt(output[6]);

                        stop.setRidersArriveHigh(ridersArriveHigh);
                        stop.setRidersArriveLow(ridersArriveLow);
                        stop.setRidersOffHigh(ridersOffHigh);
                        stop.setRidersOffLow(ridersOffLow);
                        stop.setRidersOnHigh(ridersOnHigh);
                        stop.setRidersOnLow(ridersOnLow);
                    } else {
                        LOGGER.info("Stop id: " + stopid + " not found in this scenario");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Scenario file not found", e);
        } catch (IOException e) {
            LOGGER.error("Scenario file I/O exception", e);
        }

        // Move run the first move bus event and get the result
        return getCurrentState("");
    }

    public SystemStateResponse moveBus() {
        String result = "";
            if (eventQueue.size() > 0) {
                Event event = (Event)eventQueue.poll();

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

                    Stop currentStop = transitSystem.getStops().get(currentStopId);
                    int currentPassengers = activeBus.getCurrentPassengerCount().intValue();
                    int passengerDifferential = currentStop.exchangeRiders(event.getEventRank().intValue(), currentPassengers, activeBus.getMaxPassengerCount().intValue()).intValue();

                    LOGGER.debug("Passengers before reaching stop: " + currentPassengers + "passengers after reaching stop: " + (currentPassengers + passengerDifferential));

                    activeBus.adjustPassengers(passengerDifferential);
                    // Next stop of the bus
                    Integer nextStopId = busRoute.getNextStop(currentLocation);

                    Stop nextStop = transitSystem.getStops().get(nextStopId);

                    // Distance
                    Double pathDistance = currentStop.findDistance(nextStop);
                    int travelTime = 1 + pathDistance.intValue() * 60 / activeBus.getSpeed().intValue();
                    if(currentLocation<busRoute.getStopsList().size()-1) {
                        activeBus.setCurrentLocation(currentLocation+1);
                    }else {
                        activeBus.setCurrentLocation(0);
                    }

                    // Arrival time
                    Integer nextArrivalTime = Integer.valueOf(event.getEventRank().intValue() + travelTime);
                    activeBus.setArrivalTime(nextArrivalTime.intValue());
                    eventQueue.remove(event);
                    transitSystem.getBuses().remove(busId);
                    transitSystem.getBuses().put(busId, activeBus);
                    eventQueue.add(new Event(nextArrivalTime.intValue(), "move_bus", event.getEventId().intValue()));

                    result = "b:" + activeBus.getBusId() + "->s:" + nextStop.getStopId() + "@"+ activeBus.getArrivalTime() + "//p:0/f:0";
                    LOGGER.info(result);
                }
            }
        return getCurrentState(result);
    }

    public SystemStateResponse rewind() {
        return null;
    }

    private SystemStateResponse getCurrentState(String lastEventString) {
        HashMap<Integer, Bus> buses = this.transitSystem.getBuses();
        HashMap<Integer, Stop> stops = this.transitSystem.getStops();
        HashMap<Integer, Route> routes = this.transitSystem.getRoutes();

        SystemStateResponse systemStateResponse = new SystemStateResponse();

        // Add the stops that have buses
        for (Bus bus: buses.values()) {
            int routeId = bus.getRouteId();
            int currentStop = bus.getCurrentLocation();
            int stopId = routes.get(routeId).getCurrentStop(currentStop);
            Stop stop = stops.remove(stopId);

            StateObject state = new StateObject(stopId);
            state.setPassengersAtStop(stop.getWaiting());
            state.setHasBus(true);
            state.setBusId(bus.getBusId());
            state.setPassngersOnBus(bus.getCurrentPassengerCount());
            systemStateResponse.addState(state);
        }

        // Add the stops without buses
        for (Stop stop: stops.values()){
            StateObject state = new StateObject(stop.getStopId());
            state.setPassengersAtStop(stop.getWaiting());
            systemStateResponse.addState(state);
        }

        systemStateResponse.setLastEventString(lastEventString);
        return systemStateResponse;
    }
}
