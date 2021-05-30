package com.flightScheduler.app.service;

import com.flightScheduler.app.dao.FlightDao;
import com.flightScheduler.app.model.Flight;
import com.flightScheduler.app.model.Gate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void testGetAllFlights () {
        Iterable<Flight> flights = flightService.getAllFlights();
        assertEquals(0, StreamSupport.stream(flights.spliterator(), false).count());
    }

    @Test
    public void testViewFlightById() {
        Flight flight = new Flight(1, "123ABC");
        flightDao.save(flight);

        Flight flightById = flightService.viewFlight(1);
        assertNotNull(flightById);
        assertEquals(1, flightById.getId());
        assertEquals(flight.getFlight_code(), flightById.getFlight_code());
    }
}
