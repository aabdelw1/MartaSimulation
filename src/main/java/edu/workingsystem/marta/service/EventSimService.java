package edu.workingsystem.marta.service;

import edu.workingsystem.marta.model.DiscreteEventSimulator;
import edu.workingsystem.marta.model.SystemStateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EventSimService {

    private DiscreteEventSimulator discreteEventSimulator;

    public EventSimService(){
        this.discreteEventSimulator = new DiscreteEventSimulator(50);
    }

    public SystemStateResponse startSim(MultipartFile scenarioFile, MultipartFile probabilityFile) {
        return this.discreteEventSimulator.startSim(scenarioFile, probabilityFile);
    }

    public SystemStateResponse processEvent() {
        return this.discreteEventSimulator.moveBus();
    }

    public SystemStateResponse rewind() {
        return this.discreteEventSimulator.rewind();
    }

    public SystemStateResponse reset() {
        return this.discreteEventSimulator.reset();
    }
}
