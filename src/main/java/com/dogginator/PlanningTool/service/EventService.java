package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.model.Event;

import java.util.List;

public interface EventService {
    List<Event> findAll();
    void deleteDay(Integer id);
    void saveEvent(Event event);
}
