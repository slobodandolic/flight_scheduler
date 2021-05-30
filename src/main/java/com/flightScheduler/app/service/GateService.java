package com.flightScheduler.app.service;

import com.flightScheduler.app.model.Flight;
import com.flightScheduler.app.model.Gate;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;

public interface GateService {

    Iterable<Gate> viewAllGates();

    ResponseEntity<Gate> viewGate(int gateId);

    ResponseEntity<Gate> freeGate(int gateId);

    ResponseEntity<Gate> getLandingGate(Flight flight);

    ResponseEntity<Gate> updateGateAvailabilityTime(LocalTime gateTime, int gateId);

}
