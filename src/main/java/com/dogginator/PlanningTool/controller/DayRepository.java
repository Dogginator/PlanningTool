package com.dogginator.PlanningTool.controller;

import com.dogginator.PlanningTool.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Event, Integer> {
}
