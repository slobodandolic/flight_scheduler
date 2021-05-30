package com.flightScheduler.app.controllers;

import com.flightScheduler.app.model.Flight;
import com.flightScheduler.app.model.Gate;
import com.flightScheduler.app.service.FlightServiceImpl;
import com.flightScheduler.app.service.GateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestFlightController extends RestBaseController {

    @Autowired
    FlightServiceImpl flightService;

    @Autowired
    GateServiceImpl gateService;

    @GetMapping("/flights")
    public Iterable<Flight> viewAllFlights(){
        return flightService.getAllFlights();
    }

    @PostMapping("/flight")
    public ResponseEntity<Gate> getLandingGate(@RequestBody Flight flight) {
        return gateService.getLandingGate(flight);
    }

}
