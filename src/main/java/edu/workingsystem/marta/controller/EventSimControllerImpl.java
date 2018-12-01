package edu.workingsystem.marta.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.workingsystem.marta.model.SystemStateResponse;
import edu.workingsystem.marta.service.EventSimService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class EventSimControllerImpl implements EventSimController {

    @Autowired
    private EventSimService eventSimService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventSimControllerImpl.class);

    public EventSimControllerImpl(EventSimService eventSimService) {
        this.eventSimService = eventSimService;
    }

    @Override
    public ResponseEntity<String> startSim(String scenarioFilePath, String probabilityFilePath) {
        ResponseEntity<String> response;
        ObjectMapper objectMapper = new ObjectMapper();
        SystemStateResponse state = this.eventSimService.startSim(scenarioFilePath, probabilityFilePath);
        String responseString = "";
        try {
           responseString = objectMapper.writeValueAsString(state);
        } catch (JsonProcessingException e) {
            LOGGER.debug("Error processing JSON", e);
        }
        LOGGER.info(responseString);
        if (responseString.equals("")){
            response = new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            response = new ResponseEntity(responseString, HttpStatus.OK);
        }
        return response;
    }

    @Override
    public ResponseEntity<String> processNextEvent() {
        ResponseEntity<String> response;
        ObjectMapper objectMapper = new ObjectMapper();
        SystemStateResponse state = this.eventSimService.processEvent();
        String responseString = "";
        try {
            responseString = objectMapper.writeValueAsString(state);
        } catch (JsonProcessingException e) {
            LOGGER.debug("Error processing JSON", e);
        }
        LOGGER.info(responseString);
        if (responseString.equals("")){
            response = new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            response = new ResponseEntity(responseString, HttpStatus.OK);
        }
        return response;
    }

    @Override
    public ResponseEntity<String> rewindLastEvent() {
        ResponseEntity<String> response;
        ObjectMapper objectMapper = new ObjectMapper();
        SystemStateResponse state = this.eventSimService.rewind();
        String responseString = "";
        try {
            responseString = objectMapper.writeValueAsString(state);
        } catch (JsonProcessingException e) {
            LOGGER.debug("Error processing JSON", e);
        }
        if (responseString.equals("")){
            response = new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            response = new ResponseEntity(responseString, HttpStatus.OK);
        }
        return response;
    }

    @Override
    public ResponseEntity<String> reset(String scenarioFilePath, String probabilityFilePath) {
        return startSim(scenarioFilePath, probabilityFilePath);
    }
}
