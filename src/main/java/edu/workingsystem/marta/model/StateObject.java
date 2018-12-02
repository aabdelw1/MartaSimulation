package edu.workingsystem.marta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StateObject {

    @JsonProperty("stopid")
    private String stopId;
    @JsonProperty("stopName")
    private String stopName;
    @JsonProperty("passengersAtStop")
    private String passengersAtStop;
    @JsonProperty("busid")
    private String busId;
    @JsonProperty("hasbus")
    private boolean hasBus;
    @JsonProperty("passengersOnBus")
    private String passngersOnBus;
    @JsonProperty("passengerCapacity")
    private String passengerCapacity;
    @JsonProperty("routeid")
    private String routeId;
    @JsonProperty("speed")
    private String speed;


    public StateObject(String stopId, String stopName) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.hasBus = false;
    }

    public void setHasBus(boolean hasBus) {
        this.hasBus = hasBus;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public void setPassengersAtStop(String passengersAtStop) {
        this.passengersAtStop = passengersAtStop;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public void setPassngersOnBus(String passngersOnBus) {
        this.passngersOnBus = passngersOnBus;
    }

    public void setPassengerCapacity(String passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "StateObject{" +
                "stopId=" + stopId +
                ", passengersAtStop=" + passengersAtStop +
                ", busId=" + busId +
                ", hasBus=" + hasBus +
                ", passngersOnBus=" + passngersOnBus +
                ", passengerCapacity=" + passengerCapacity +
                '}';
    }
}
