package com.dogginator.PlanningTool.controller;

import com.dogginator.PlanningTool.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DayRepository extends JpaRepository<Event, Integer> {
    List<Event> findAll(String date);
}
