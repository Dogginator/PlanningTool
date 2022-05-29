package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.controller.EventException;
import com.dogginator.PlanningTool.model.Event;

import java.util.List;

public interface EventService {
    List<Event> findAll();
    void deleteDay(Integer id);
    void createEvent(Event event) throws EventException;
    Event getEventById(Integer id);
    void saveEvent(Event event)throws EventException;
    void automaticRemoveOldDatesInDatabase( List<Event> eventList);
}
