package edu.workingsystem.marta.model;

import edu.workingsystem.marta.util.EventComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class DiscreteEventSimulator {

    private MassTransitSystem transitSystem;
    private Comparator comp;
    private PriorityQueue<Event> eventQueue;
    private MultipartFile configFileCopy;
    private MultipartFile probabilityFileCopy;

    private ArrayList<PriorityQueue<Event>> lastThreeEvents;
    private ArrayList<ArrayList<Integer>> allOutput;
    private ArrayList<SystemStateResponse> lastThreeResponses;
    
    private Integer K_SPEED;
    private Integer K_CAPACITY;
    private Integer K_WAITING;
    private Integer K_BUSES;
    private Integer K_COMBINED;

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscreteEventSimulator.class);

    public DiscreteEventSimulator(int capacity){
        this.transitSystem = new MassTransitSystem();
        this.comp = new EventComparator();
        this.eventQueue = new PriorityQueue(capacity, this.comp);
        this.lastThreeEvents = new ArrayList<>();
        this.allOutput = new ArrayList<>();
        this.lastThreeResponses = new ArrayList<>();
    }

    public SystemStateResponse startSim(MultipartFile configFile, MultipartFile probabilityFile, int kspd, int kcap, int kwait, int kbus, int k_COMBINED) {

        this.K_SPEED = kspd;
        this.K_CAPACITY = kcap;
        this.K_BUSES = kbus;
        this.K_WAITING = kwait;
        this.K_COMBINED = k_COMBINED;


        this.configFileCopy = configFile;
        this.probabilityFileCopy = probabilityFile;

        String result = "";


        try {
            InputStream inputStream = configFile.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
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


        try {
            InputStream inputStream = probabilityFile.getInputStream();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(inputStream));
            String st;

            while ((st = br2.readLine()) != null) {

                String[] output = st.split("\\,");
                String stopStr = output[0];

                Integer stopid = Integer.parseInt(stopStr);

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
                        LOGGER.debug("Stop id: " + stopid + " not found in this scenario");
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

        SystemStateResponse response = new SystemStateResponse();
        PriorityQueue<Event> copy = copyQueue();

        if(lastThreeEvents.size() <=3) {
            lastThreeEvents.add(copy);
        }
        else {
            lastThreeEvents.remove(0);
            lastThreeEvents.add(copy);
        }

        String result = "";
            if (eventQueue.size() > 0) {
                Event event = (Event)eventQueue.poll();

                if ("move_bus".equals(event.getEvent_type())) {
                    ArrayList<Integer> currentState = new ArrayList<>();
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
                    LOGGER.debug("Current Stop:");
                    LOGGER.debug(currentStop + "");
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

                    currentState.add(activeBus.getBusId());
                    currentState.add(nextStop.getStopId());
                    currentState.add(activeBus.getArrivalTime());

                    response = getCurrentState(result);
                    if(allOutput.size() <=3) {
                        allOutput.add(currentState);
                        lastThreeResponses.add(response);
                    }
                    else {
                        allOutput.remove(0);
                        allOutput.add(currentState);
                        lastThreeResponses.remove(0);
                        lastThreeResponses.add(response);
                    }
                }
            }
        return response;
    }

    public SystemStateResponse rewind() {

        SystemStateResponse response;
        String result = "";
        if(lastThreeEvents.size() > 0) {
            PriorityQueue<Event> lastState;
            ArrayList<Integer> lastStateValues;
            int lastStateIndex = lastThreeEvents.size()-1;

            lastState = lastThreeEvents.get(lastStateIndex);
            lastStateValues = allOutput.get(lastStateIndex);
            response = lastThreeResponses.get(lastStateIndex);

            this.eventQueue = lastState;

            result = "b:" + lastStateValues.get(0) + "->s:" + lastStateValues.get(1) + "@"+ lastStateValues.get(2) + "//p:0/f:0";
            LOGGER.info(result);
            lastThreeEvents.remove(lastState);
            allOutput.remove(lastStateValues);
            lastThreeResponses.remove(response);
        } else {
            response = getCurrentState(result);
        }

        return response;

    }

    private SystemStateResponse getCurrentState(String lastEventString) {
        Map<Integer, Bus> buses = new HashMap<>(this.transitSystem.getBuses());
        Map<Integer, Stop> stops = new HashMap<>(this.transitSystem.getStops());
        Map<Integer, Route> routes = new HashMap<>(this.transitSystem.getRoutes());

        Set stopsWithBus = new HashSet();

        SystemStateResponse systemStateResponse = new SystemStateResponse();

        // Add the stops that have buses
        for (Bus bus: buses.values()) {
            int routeId = bus.getRouteId();
            int currentStop = bus.getCurrentLocation();
            int stopId = routes.get(routeId).getCurrentStop(currentStop);
            Stop stop = stops.get(stopId);

            stopsWithBus.add(stopId);

            StateObject state = new StateObject(String.valueOf(stopId), stop.getStopName());
            if (null!=stop.getWaiting()) {
                state.setPassengersAtStop(String.valueOf(stop.getWaiting()));
            } else {
                state.setPassengersAtStop(String.valueOf(0));
            }
            state.setHasBus(true);
            state.setBusId(String.valueOf(bus.getBusId()));
            state.setPassngersOnBus(String.valueOf(bus.getCurrentPassengerCount()));
            state.setPassengerCapacity(String.valueOf(bus.getMaxPassengerCount()));
            state.setRouteId(String.valueOf(routeId));
            state.setSpeed(String.valueOf(bus.getSpeed()));
            systemStateResponse.addState(state);
        }

        // Add the stops without buses
        for (Stop stop: stops.values()){
            if (!stopsWithBus.contains(stop.getStopId())) {
                StateObject state = new StateObject(String.valueOf(stop.getStopId()), stop.getStopName());
                state.setPassengersAtStop(String.valueOf(stop.getWaiting()));
                systemStateResponse.addState(state);
            }
        }

        systemStateResponse.setLastEventString(lastEventString);
        systemStateResponse.setEfficiency(calculateEfficiency());

        return systemStateResponse;
    }

    private PriorityQueue<Event> copyQueue() {
        PriorityQueue<Event> copy = new PriorityQueue<>(this.eventQueue.size(), this.comp);
        for (Event e: this.eventQueue){
            copy.add(e);
        }
        return copy;
    }

    public SystemStateResponse reset() {
        SystemStateResponse stateResponse;
        if (this.configFileCopy != null && this.probabilityFileCopy != null) {
            stateResponse = new SystemStateResponse();
            stateResponse.setLastEventString("Missing Config or Probability Files");
        } else {
            stateResponse = startSim(this.configFileCopy, this.probabilityFileCopy, this.K_SPEED, this.K_CAPACITY, this.K_WAITING, this.K_BUSES, this.K_COMBINED);
        }
        return stateResponse;
    }

    public Boolean updateBus(int bid, int rid, int spd, int capacity) {
        HashMap<Integer, Bus> buses = this.transitSystem.getBuses();
        HashMap<Integer, Route> routes = this.transitSystem.getRoutes();

        if (!buses.containsKey(bid) || !routes.containsKey(rid)){
            return false;
        }

        Bus bus = buses.get(bid);
        bus.setRouteId(rid);
        bus.setSpeed(spd);

        if (bus.getCurrentPassengerCount() > capacity){
            bus.setCurrentPassengerCount(capacity);
        }

        bus.setMaxPassengerCount(capacity);

        buses.put(bid, bus);
        this.transitSystem.setBuses(buses);

        return true;
    }

    private String calculateEfficiency() {
       Integer totalWaitingPassengers=0;
       Integer busCost=0;
       
       HashMap<Integer, Stop> stopMap= this.transitSystem.getStops();
       HashMap<Integer, Bus> busMap= this.transitSystem.getBuses();
       
       for(Bus bus:busMap.values()) {
    	   busCost+=(K_SPEED*bus.getSpeed())+(K_CAPACITY*bus.getMaxPassengerCount());
       }
       for(Stop stop:stopMap.values()) {
    	   totalWaitingPassengers+=stop.getWaiting();
       }
       
       Integer systemEfficiency=0;
       systemEfficiency=(K_WAITING*totalWaitingPassengers)+(K_BUSES*busCost)+K_COMBINED*totalWaitingPassengers*busCost;
       return systemEfficiency.toString();
    }
}
