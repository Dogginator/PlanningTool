package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.controller.DayRepository;
import com.dogginator.PlanningTool.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    DayRepository dayRepo;

    @Override
    public List<Event> findAllEventOnDay(String date){return dayRepo.findAll(date);}
    @Override
    public List<Event> findTotalWeek(String id){return dayRepo.findAll(id);}
    @Override
    public void deleteDay(Integer id){dayRepo.deleteById(id);}

}
