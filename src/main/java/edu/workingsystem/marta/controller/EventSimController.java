package edu.workingsystem.marta.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin
@RequestMapping("/")
@Api(value = "Event Sim Controller")
public interface EventSimController {

    @ApiOperation(value = "Start the simulation by receiving the path to the scenario file and the probability parameter file",
        response = String.class)
    @RequestMapping(value = "/start/{kspd}/{kcap}/{kwait}/{kbus}/{kcomb}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> startSim(@RequestParam("configList") MultipartFile[] configList, @RequestParam("kspd") String kspd, @RequestParam("kcap") String kcap, @RequestParam("kwait") String kwait, @RequestParam("kbus") String kbus, @RequestParam("kcomb") String kcomb);

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
    @RequestMapping(value = "/updateBus/{busId}/{routeId}/{speed}/{capacity}", method = RequestMethod.GET)
    ResponseEntity<Boolean> updateBus(@RequestParam("busId") String busId, @RequestParam("routeId") String routeId,
                                      @RequestParam("speed") String speed, @RequestParam("capacity") String capacity);

}
