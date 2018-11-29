package edu.workingsystem.marta.service;

import edu.workingsystem.marta.model.DiscreteEventSimulator;
import org.springframework.stereotype.Service;

@Service
public class EventSimService {

    private DiscreteEventSimulator discreteEventSimulator;

    public EventSimService(){
        this.discreteEventSimulator = new DiscreteEventSimulator(50);
    }

    public String startSim(String scenarioFilePath, String probabilityFilePath) {
        return this.discreteEventSimulator.startSim(scenarioFilePath, probabilityFilePath);
    }

    public String processEvent() {
        return this.discreteEventSimulator.moveBus();
    }

    public String rewind() {
        return this.discreteEventSimulator.rewind();
    }
}
