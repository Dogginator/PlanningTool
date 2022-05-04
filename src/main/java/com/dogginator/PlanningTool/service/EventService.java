package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.model.Event;

import java.util.List;

public interface EventService {
    List<Event> findAllEventOnDay(String date);
    List<Event> findTotalWeek(String id);
    void deleteDay(Integer id);
}
