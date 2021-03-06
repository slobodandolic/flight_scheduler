package com.flightScheduler.app.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import com.flightScheduler.app.dao.FlightDao;
import com.flightScheduler.app.dao.GateDao;
import com.flightScheduler.app.exceptions.*;
import com.flightScheduler.app.model.Flight;
import com.flightScheduler.app.model.Gate;
import com.flightScheduler.app.model.Occupied;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GateServiceImpl implements GateService {

    private static final String NO_GATE_FOUND = "Gate with the given id is not present: ";
    private static final String NO_GATE_AVAILABLE = "No free gates available at this time.";
    private static final String INVALID_TIME_FORMAT = "You must provide valid time format.";
    private static final String INVALID_FLIGHT_CODE = "Provided flight code is invalid or flight has been assigned a gate: ";

    @Autowired
    GateDao gateDao;

    @Autowired
    FlightDao flightDao;

    @Override
    public Iterable<Gate> viewAllGates() {
        return gateDao.findAll();
    }

    @Override
    public ResponseEntity<Gate> viewGate(int gateId) {
        Optional<Gate> findById = gateDao.findById(gateId);

        try {
            if (!findById.isPresent()) {
                throw new RecordNotPresentException(NO_GATE_FOUND + gateId);
            }
            Gate gate = findById.get();
            return new ResponseEntity<Gate>(gate, HttpStatus.OK);
        } catch (RecordNotPresentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Gate> freeGate(int gateId) {
        Optional<Gate> findById = gateDao.findById(gateId);

        try {
            if (!findById.isPresent()) {
                throw new RecordNotPresentException(NO_GATE_FOUND + gateId);
            }
            Gate gate = findById.get();
            gate.setOccupied(Occupied.FREE);
            gate.setFlight_code("");
            gateDao.save(gate);
            return new ResponseEntity<Gate>(gate, HttpStatus.OK);
        } catch (RecordNotPresentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Gate> updateGateAvailabilityTime(LocalTime gateTime, int gateId) {
        Optional<Gate> findById = gateDao.findById(gateId);

        try {
            if (!findById.isPresent()) {
                throw new RecordNotPresentException(NO_GATE_FOUND + gateId);
            }

            String time = gateTime.truncatedTo(ChronoUnit.MINUTES).toString();
            if (!validateTimeFormat(time)) {
                throw new InvalidTimeFormatException(INVALID_TIME_FORMAT);
            }

            Gate gate = findById.get();
            gate.setTime_available(gateTime);
            gateDao.save(gate);
            return new ResponseEntity<Gate>(gate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Gate> getLandingGate(Flight flight) {
        List<Gate> allGates = gateDao.findAll();

        try {
            if (checkIfFlightIsAlreadyLanded(flight, allGates) || !validateFlightCode(flight.getFlight_code())) {
                throw new InvalidFlightCodeException(INVALID_FLIGHT_CODE + flight.getFlight_code());
            }
        } catch (InvalidFlightCodeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            if (allGates.isEmpty()) {
                throw new NoAvailableGatesException(NO_GATE_AVAILABLE);
            }
            Optional<Gate> availableGate = allGates.stream().filter(isGateAvailable).findAny();

            if (!availableGate.isPresent()) {
                throw new NoAvailableGatesException(NO_GATE_AVAILABLE);
            }

            Gate gate = availableGate.get();
            gate.setOccupied(Occupied.OCCUPIED);
            gate.setFlight_code(flight.getFlight_code());
            gateDao.save(gate);
            flightDao.save(flight);
            return new ResponseEntity<Gate>(availableGate.get(), HttpStatus.OK);
        } catch (NoAvailableGatesException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Checks if the gate status is FREE and if the gate is available at this time
     */
    private Predicate<Gate> isGateAvailable = gate -> gate.getOccupied().equals(Occupied.FREE) && LocalTime.now().isBefore(gate.getTime_available());

    /**
     * Method to check if the flight code provided is valid.
     * Valid code should have 6 characters and consist of only numbers and letters - not case sensitive
     *
     * @param code flight_code to be checked
     * @return true if code matches the conditions
     */
    private boolean validateFlightCode(String code) {
        String regex = "[A-Za-z0-9]+";
        int validCodeLength = 6;
        return code.length() == validCodeLength && Pattern.matches(regex, code);
    }

    /**
     * The valid time in the 24-hour format must satisfy the following conditions.
     * It should start from 0-23 or 00-23.
     * It should be followed by a ???:'(colon).
     * It should be followed by two digits from 00 to 59.
     * It should not end with ???am???, ???pm??? or ???AM???, ???PM???.
     * Maximum value supported 23:59:59
     * @param time String representation of time based on the rules mentioned
     * @return true if time format is validated
     */
    private boolean validateTimeFormat(String time) {
        if (time == null) {
            return false;
        }
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        return Pattern.matches(regex, time);
    }

    /**
     * Check if flight has been assigned a gate already
     * @param flight flight to be checked
     * @param list   list of gates available
     * @return true if flight has been assigned a gate, false if flight hasn't been assigned a gate
     */
    private boolean checkIfFlightIsAlreadyLanded(Flight flight, List<Gate> list) {
        String flightCode = flight.getFlight_code();
        return list.stream().anyMatch(gate -> gate.getFlight_code().equals(flightCode));
    }
}
