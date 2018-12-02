package edu.workingsystem.marta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SystemStateResponse {

    @JsonProperty("stateList")
    private List<StateObject> stateList;
    @JsonProperty("lastEventString")
    private String lastEventString;
    @JsonProperty("systemEfficiency")
    private String efficiency;

    public SystemStateResponse(){
        this.stateList = new ArrayList();
    }

    public void addState(StateObject state){
        this.stateList.add(state);
    }

    public void setLastEventString(String lastEventString) {
        this.lastEventString = lastEventString;
    }

    public void setEfficiency(String efficiency) {
        this.efficiency = efficiency;
    }

    @Override
    public String toString() {
        return "SystemStateResponse{" +
                "stateList=" + stateList +
                ", lastEventString='" + lastEventString + '\'' +
                '}';
    }
}
