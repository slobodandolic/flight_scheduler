package com.flightScheduler.app.service;

import com.flightScheduler.app.model.Flight;
import com.flightScheduler.app.model.Gate;
import com.flightScheduler.app.model.Occupied;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class GateServiceTest {

    @Autowired
    GateServiceImpl gateService;

    @Test
    public void testViewAllGates() {
        Iterable<Gate> gates = gateService.viewAllGates();

        assertNotNull(gates);
        assertEquals(5, StreamSupport.stream(gates.spliterator(), false).count());
    }

    @Test
    public void testViewGateById() {
        ResponseEntity<Gate> gateResponseEntity = gateService.viewGate(1);

        Gate gate = gateResponseEntity.getBody();

        assertNotNull(gate);
        assertEquals(gate.getId(), 1);
        assertEquals(gate.getOccupied(), Occupied.FREE);
    }

    @Test
    public void testViewGateByInvalidId() {
        ResponseEntity<Gate> gateResponseEntity = gateService.viewGate(6);
        assertEquals("Gate with the given id is not present: 6", gateResponseEntity.getBody());
    }

    @Test
    public void testFreeGate() {
        ResponseEntity<Gate> gateResponseEntity = gateService.freeGate(3);
        Gate gate = gateResponseEntity.getBody();

        assert gate != null;
        assertEquals(Occupied.FREE, gate.getOccupied());
        assertEquals("", gate.getFlight_code());
    }

    @Test
    public void testFreeGateWithInvalidId() {
        ResponseEntity<Gate> gateResponseEntity = gateService.freeGate(6);
        assertEquals("Gate with the given id is not present: 6", gateResponseEntity.getBody());
    }

    @Test
    public void testUpdateGateAvailabilityTime() {
        ResponseEntity<Gate> gateResponseEntity = gateService.updateGateAvailabilityTime(LocalTime.now(), 2);

        Gate gate = gateResponseEntity.getBody();
        assert gate != null;
        assertEquals(LocalTime.now().truncatedTo(ChronoUnit.MINUTES), gate.getTime_available().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    public void testUpdateGateAvailabilityTimeWithInvalidId() {
        ResponseEntity<Gate> gateResponseEntity = gateService.updateGateAvailabilityTime(LocalTime.now(), 6);
        assertEquals("Gate with the given id is not present: 6", gateResponseEntity.getBody());
    }

    @Test
    public void testGetLandingGate() {
        Flight flight = new Flight(1, "ABC123");
        ResponseEntity<Gate> gateResponseEntity = gateService.getLandingGate(flight);

        Gate gate = gateResponseEntity.getBody();

        assert gate != null;
        assertEquals(gate.getOccupied(), Occupied.OCCUPIED);
        assertEquals(gate.getFlight_code(), flight.getFlight_code());
    }

    @Test
    public void testGetLandingGateWithInvalidFlightCode() {
        Flight flight = new Flight(1, "ABC1234");
        ResponseEntity<Gate> gateResponseEntity = gateService.getLandingGate(flight);
        assertEquals("Provided flight code is invalid or flight has been assigned a gate: " + flight.getFlight_code(), gateResponseEntity.getBody());
    }


}
