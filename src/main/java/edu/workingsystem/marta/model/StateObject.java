package edu.workingsystem.marta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StateObject {

    @JsonProperty("stopid")
    private int stopId;
    @JsonProperty("passengersAtStop")
    private int passengersAtStop;
    @JsonProperty("busid")
    private int busId;
    @JsonProperty("hasbus")
    private boolean hasBus;
    @JsonProperty("passengersOnBus")
    private int passngersOnBus;
    @JsonProperty("passengerCapacity")
    private int passengerCapacity;

    public StateObject(int stopId) {
        this.stopId = stopId;
        this.hasBus = false;
        this.passengersAtStop = -1;
        this.passngersOnBus = -1;
        this.passengerCapacity = -1;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public void setPassengersAtStop(int passengersAtStop) {
        this.passengersAtStop = passengersAtStop;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public void setHasBus(boolean hasBus) {
        this.hasBus = hasBus;
    }

    public void setPassngersOnBus(int passngersOnBus) {
        this.passngersOnBus = passngersOnBus;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
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
