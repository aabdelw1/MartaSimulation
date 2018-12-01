package edu.workingsystem.marta.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/")
@Api(value = "Event Sim Controller")
public interface EventSimController {

    @ApiOperation(value = "Start the simulation by receiving the path to the scenario file and the probability parameter file",
        response = String.class)
    @RequestMapping(value = "/start/", method = RequestMethod.POST)
    ResponseEntity<String> startSim(@RequestParam("scenario") MultipartFile scenarioFile, @RequestParam MultipartFile probabilityFile);


    @ApiOperation(value = "Process the next event in the simulation", response = String.class)
    @RequestMapping(value = "/processEvent", method = RequestMethod.GET)
    ResponseEntity<String> processNextEvent();

    @ApiOperation(value = "Rewind the last event in the simulation", response = String.class)
    @RequestMapping(value = "/rewind", method = RequestMethod.GET)
    ResponseEntity<String> rewindLastEvent();

    @ApiOperation(value = "Reset the simulation", response = String.class)
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    ResponseEntity<String> reset();


}
