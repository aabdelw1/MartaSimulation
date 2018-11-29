package edu.workingsystem.marta.controller;

import edu.workingsystem.marta.service.EventSimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EventSimControllerImpl implements EventSimController {

    @Autowired
    private EventSimService eventSimService;

    public EventSimControllerImpl(EventSimService eventSimService) {
        this.eventSimService = eventSimService;
    }

    @Override
    public ResponseEntity<String> startSim(String scenarioFilePath, String probabilityFilePath) {
        String s = this.eventSimService.startSim(scenarioFilePath, probabilityFilePath);
        ResponseEntity<String> response = new ResponseEntity(s, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<String> processNextEvent() {
        String s = this.eventSimService.processEvent();
        ResponseEntity<String> response = new ResponseEntity(s, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<String> rewindLastEvent() {
        String s = this.eventSimService.rewind();
        ResponseEntity<String> response = new ResponseEntity(s, HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseEntity<String> reset(String scenarioFilePath, String probabilityFilePath) {
        return startSim(scenarioFilePath, probabilityFilePath);
    }
}
