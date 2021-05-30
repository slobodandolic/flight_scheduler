package com.flightScheduler.app.controllers;

import com.flightScheduler.app.model.Gate;
import com.flightScheduler.app.service.GateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestGateController extends RestBaseController {

    @Autowired
    GateServiceImpl gateService;

    @GetMapping("/gates")
    public Iterable<Gate> viewAllGates() {
        return gateService.viewAllGates();
    }

    @GetMapping(value = "/gates/{id}")
    public ResponseEntity<Gate> viewGate(@PathVariable("id") int id) {
        return gateService.viewGate(id);
    }

    @PutMapping("/gates")
    public ResponseEntity<Gate> updateGateAvailablityTime(@RequestBody Gate gate) {
        return gateService.updateGateAvailabilityTime(gate.getTime_available(), gate.getId());
    }

    @PutMapping("/gates/freeGate")
    public ResponseEntity<Gate> freeGate(@RequestBody Gate gate) {
        return gateService.freeGate(gate.getId());
    }
}