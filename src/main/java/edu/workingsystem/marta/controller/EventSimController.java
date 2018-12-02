package edu.workingsystem.marta.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RequestMapping("/")
@Api(value = "Event Sim Controller")
public interface EventSimController {

    @ApiOperation(value = "Start the simulation by receiving the path to the scenario file and the probability parameter file",
        response = String.class)
    @RequestMapping(value = "/start/", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> startSim(@RequestParam("configList") MultipartFile[] configList);

    @ApiOperation(value = "Process the next event in the simulation", response = String.class)
    @RequestMapping(value = "/processEvent", method = RequestMethod.GET)
    ResponseEntity<String> processNextEvent();

    @ApiOperation(value = "Rewind the last event in the simulation", response = String.class)
    @RequestMapping(value = "/rewind", method = RequestMethod.GET)
    ResponseEntity<String> rewindLastEvent();

    @ApiOperation(value = "Reset the simulation", response = String.class)
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    ResponseEntity<String> reset();

    @ApiOperation(value = "Update bus", response = Boolean.class)
    @RequestMapping(value = "/updateBus/{busId}/{routeId}/{speed}", method = RequestMethod.GET)
    ResponseEntity<Boolean> updateBus(@RequestParam("busId") String busId, @RequestParam("routeId") String routeId,
                                      @RequestParam("speed") String speed);

}
