package edu.workingsystem.marta.service;

import edu.workingsystem.marta.model.DiscreteEventSimulator;
import edu.workingsystem.marta.model.SystemStateResponse;
import org.springframework.stereotype.Service;

@Service
public class EventSimService {

    private DiscreteEventSimulator discreteEventSimulator;

    public EventSimService(){
        this.discreteEventSimulator = new DiscreteEventSimulator(50);
    }

    public SystemStateResponse startSim(String scenarioFilePath, String probabilityFilePath) {
        return this.discreteEventSimulator.startSim(scenarioFilePath, probabilityFilePath);
    }

    public SystemStateResponse processEvent() {
        return this.discreteEventSimulator.moveBus();
    }

    public SystemStateResponse rewind() {
        return this.discreteEventSimulator.rewind();
    }
}
