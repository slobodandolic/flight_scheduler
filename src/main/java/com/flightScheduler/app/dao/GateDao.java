package com.flightScheduler.app.dao;

import com.flightScheduler.app.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GateDao extends JpaRepository<Gate, Integer> {
}
