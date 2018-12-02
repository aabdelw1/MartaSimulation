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

    public SystemStateResponse startSim(MultipartFile scenarioFile, MultipartFile probabilityFile, String kspd, String kcap, String kwait, String kbus, String kcomb) {
        return this.discreteEventSimulator.startSim(scenarioFile, probabilityFile, 1, 1, 1, 1, 1);
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

    public Boolean updateBus(int bid, int rid, int spd, int capacity) {
        return this.discreteEventSimulator.updateBus(bid, rid, spd, capacity);
    }
}
