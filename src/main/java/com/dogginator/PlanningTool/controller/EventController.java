package com.dogginator.PlanningTool.controller;


import com.dogginator.PlanningTool.service.DateService;
import com.dogginator.PlanningTool.model.Event;
import com.dogginator.PlanningTool.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventController {
private Event event;

    @Autowired
    EventService eventService;
    DateService dateService;

    @RequestMapping(value = "/planningTool", method = RequestMethod.GET)
    public String home(Model model){
        automaticRemoveOldDatesInDatabase();
        List<Event> eventToday = getEventToday();
        model.addAttribute("eventToday", eventToday);
        return "Index";
    }
    @RequestMapping(value = "/planningTool/index/delete/{id}", method = RequestMethod.DELETE)
    public String removeDayInIndex(@PathVariable("id")Integer id ){
        eventService.deleteDay(id);
        return "redirect:/planningTool";
    }

    @RequestMapping(value = "/planningTool/planning", method = RequestMethod.POST)
    public String planning(Model model){
        List<Integer> startTimeList = getStartTimeList();
        List<Integer> endTimeList = getEndTimeList();
        model.addAttribute("event", new Event());
        model.addAttribute("startTimeList", startTimeList);
        model.addAttribute("endTimeList", endTimeList);
        return "Planning";// TODO SET UP planning.html
    }
    @RequestMapping(value = "/planningTool/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("event") Model model, Event event, RedirectAttributes redirectAttributes) {

        model.addAttribute("event", event);
        String date = dateService.planningCheck(event.isThisWeek());
        List<Event> eventList = eventService.findAll();
        List<Event> filterList = eventList.stream().filter(e -> date.equals(e.getDate())).collect(Collectors.toList());

        try {
            for(Event event1 : filterList){
                if(event1.getStartAt() != event.getStartAt() ){
                    eventService.saveEvent(event);
                }
                redirectAttributes.addAttribute("message", "Event has been added");
            }
        }catch (EventException e){
            redirectAttributes.addAttribute("message", e.getMessage());
            return "redirect:/planningTool/planning";
        }


        return "Index"; // TODO SET UP a dashboard = Index
    }

    @RequestMapping(value = "/planningTool/weekly", method = RequestMethod.GET)
    public String week(Model model){
        List<Event> firstDay, secondDay, thirdDay, fourthDay, fifthDay, sixthDay, seventhDay;
        List<Event> eventList = eventService.findAll();
        String today = LocalDate.now().toString();
        firstDay = eventList.stream().filter(event -> today.equals(event.getDate())).collect(Collectors.toList());
        model.addAttribute("firstDay", firstDay);
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

    private List<Integer> getStartTimeList(){
        List<Integer> timeList = new ArrayList<>();
        for(int i = 5; i <= 18; i++ ){
            timeList.add(i);
        }
        return timeList;
    }
    private List<Integer> getEndTimeList(){
        List<Integer> timeList = new ArrayList<>();
        for(int i = 6; i <= 18; i++ ){
            timeList.add(i);
        }
        return timeList;
    }
}
