package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.controller.DayRepository;
import com.dogginator.PlanningTool.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    DayRepository dayRepo;


    @Override
    public void saveEvent(Event event) {
        dayRepo.save(event);
    }

    @Override
    public List<Event> findAll(){return dayRepo.findAll();}
    @Override
    public void deleteDay(Integer id){dayRepo.deleteById(id);}

}
