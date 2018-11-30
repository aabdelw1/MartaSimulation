package edu.workingsystem.marta.model;

public class StateObject {

    private int stopId;
    private int passengersAtStop;
    private int busId;
    private boolean hasBus;
    private int passngersOnBus;
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
}
