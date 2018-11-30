package edu.workingsystem.marta.model;

import java.util.ArrayList;
import java.util.List;

public class SystemStateResponse {

    private List<StateObject> stateList;
    private String lastEventString;

    public SystemStateResponse(){
        this.stateList = new ArrayList();
    }

    public void addState(StateObject state){
        this.stateList.add(state);
    }

    public void setLastEventString(String lastEventString) {
        this.lastEventString = lastEventString;
    }
}
