package com.flightScheduler.app.service;

import com.flightScheduler.app.dao.FlightDao;
import com.flightScheduler.app.model.Flight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class FlightServiceTest {

    @Autowired
    FlightServiceImpl flightService;

    @Autowired
    FlightDao flightDao;

    @Test
    public void testGetAllFlights() {
        Iterable<Flight> flights = flightService.getAllFlights();
        assertEquals(0, StreamSupport.stream(flights.spliterator(), false).count());
    }

    @Test
    public void testViewFlightById() {
        Flight flight = new Flight(2,"123ABC");
        flightDao.save(flight);

        ResponseEntity<Flight> flightResponseEntity = flightService.viewFlight(2);
        Flight flightReturned = flightResponseEntity.getBody();
        assertNotNull(flightReturned);
        assertEquals(2, flightReturned.getId());
        assertEquals(flight.getFlight_code(), flightReturned.getFlight_code());
    }
}
