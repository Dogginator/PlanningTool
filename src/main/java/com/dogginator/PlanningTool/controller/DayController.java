package com.dogginator.PlanningTool.controller;


import com.dogginator.PlanningTool.service.DateService;
import com.dogginator.PlanningTool.model.Event;
import com.dogginator.PlanningTool.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DayController {
private Event event;

    @Autowired
    EventService eventService;
    DateService dateService;

    @RequestMapping(value = "/planningTool/dashboard", method = RequestMethod.GET)
    public String homePage(Model model){
        automaticRemoveOldDatesInDatabase();
        List<Event> weekList = getEventToday();
        model.addAttribute("weekList", weekList);
        return "Index";
    }

    @RequestMapping(value = "/planningTool/planning", method = RequestMethod.POST)
    public String postDay(Model model){
        model.addAttribute("event", new Event());
        return "Planning";// TODO SET UP createDay
    }
    @RequestMapping(value = "/planningTool/dashboard", method = RequestMethod.POST)
    public String saveDayToDB(Model model, Event day){

        model.addAttribute("day", day);
        return "Index"; // TODO SET UP a dashboard
    }

    @RequestMapping(value = "/planningTool/weekly", method = RequestMethod.GET)
    public String getCurrentWeek(Model model){
        List<Event> firstDay, secondDay, thirdDay, fourthDay, fifthDay, sixthDay, seventhDay;
        List<Event> eventList = eventService.findAll();
        String today = LocalDate.now().toString();
        firstDay = eventList.stream().filter(event -> today.equals(event.getDate())).collect(Collectors.toList());
        model.addAttribute("fistDay", firstDay);
        secondDay = getNextDay(1);
        model.addAttribute("secondDay", secondDay);
        thirdDay = getNextDay(2);
        model.addAttribute("thirdDay", thirdDay);
        fourthDay = getNextDay(3);
        model.addAttribute("fourthDay", fourthDay);
        fifthDay = getNextDay(4);
        model.addAttribute("fifthDay", fifthDay);
        sixthDay = getNextDay(5);
        model.addAttribute("sixthDay", sixthDay);
        seventhDay = getNextDay(6);
        model.addAttribute("seventhDay", seventhDay);
        return "Weekly";// TODO set up weekly and look in to how to do more and less tables to display in frontend
    }

    @RequestMapping(value = "/planningTool/remove/plan/{id}", method = RequestMethod.DELETE)
    public String removeDay(@PathVariable("id")Integer id ){
        eventService.deleteDay(id);
        return "redirect:/planningTool/weekly";
    }

    private void automaticRemoveOldDatesInDatabase(){
        List<Event> eventList = eventService.findAll();
        List<Event> removalDays = eventList.stream()
                .filter(event -> dateService.removeDaysToDate(1).equals(event.getDate())).collect(Collectors.toList());
        if(removalDays.size() != 0){
            for (Event event : removalDays){
                int currentID = event.getDayId();
                eventService.deleteDay(currentID);
            }
        }
    }
    private List<Event> getEventToday(){
        String today = LocalDate.now().toString();
        List<Event> eventList = eventService.findAll();
        return eventList.stream()
                .filter(event -> today.equals(event.getDate())).collect(Collectors.toList());
    }

    private List<Event> getNextDay(int nextDay){
        List<Event> eventList = eventService.findAll();
        return eventList.stream().filter(event -> dateService.addDaysToDate(nextDay).equals(event.getDate())).collect(Collectors.toList());
    }
}
