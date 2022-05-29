package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.controller.EventException;
import com.dogginator.PlanningTool.controller.EventRepository;
import com.dogginator.PlanningTool.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EventServiceImpl implements EventService {
    ListService listService = new ListService();
    DateService dateService = new DateService();

    @Autowired
    EventRepository dayRepo;


    @Override
    public void createEvent(Event event) throws EventException{
        List<Event> eventList = findAll();
        List<Event> tryList = listService.checkEventByTime(event, eventList);
        if(tryList.size() != 0){
            for(Event event1 : tryList){
                if(event1.getStartAt().equals(event.getStartAt())){
                    throw new EventException("ERROR: The time: "+ event.getStartAt() + " , At given date: " + event.getDate() +  ", is already booked by another event");

                }
            }
        }else{
            dayRepo.save(event);}
        }



    @Override
    public Event getEventById(Integer id){return dayRepo.getById(id);}

    @Override
    public List<Event> findAll(){return dayRepo.findAll();}

    @Override
    public void deleteDay(Integer id){dayRepo.deleteById(id);}

    @Override
    public void saveEvent(Event event)throws EventException{
        List<Event> eventList = findAll();
        List<Event> tryList = listService.checkEventByTime(event, eventList);
        if(tryList.size() != 0){
            for(Event event1 : tryList){
                if(event1.getStartAt().equals(event.getStartAt())){
                    throw new EventException("ERROR: The time: "+ event.getStartAt() + " , At given date: " + event.getDate() +  ", is already booked by another event");

                }
            }
        }else{
            dayRepo.save(event);}
    }
    @Override
    public void automaticRemoveOldDatesInDatabase( List<Event> eventList){
        if(eventList.size() != 0){
            List<Event> removalDays = eventList.stream()
                    .filter(event -> dateService.removeDaysToDate(1).equals(event.getDate())).collect(Collectors.toList());
            if(removalDays.size() != 0){
                for (Event event : removalDays){
                    int currentID = event.getDayId();
                    dayRepo.deleteById(currentID);
                }
            }
        }

    }


}
