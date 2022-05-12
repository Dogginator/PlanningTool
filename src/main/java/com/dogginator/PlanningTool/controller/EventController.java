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

    @RequestMapping(value = "/planningTool/planning", method = RequestMethod.GET)
    public String planning(Model model){
        List<Integer> startTimeList = getStartTimeList();
        List<Integer> endTimeList = getEndTimeList();
        List<String> dayNameList = getSevenDays();
        model.addAttribute("event", new Event());
        model.addAttribute("dayNameList", dayNameList);
        model.addAttribute("startTimeList", startTimeList);
        model.addAttribute("endTimeList", endTimeList);
        return "Planning";// TODO fix planning.html
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
                    eventService.createEvent(event);
                }
                redirectAttributes.addFlashAttribute("message", "Event has been added");
            }
        }catch (EventException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "Index";
        }

        return "redirect:/planningTool"; // TODO fix Index
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
        return "Weekly";// TODO fix weekly
    }
    @RequestMapping(value = "/planningTool/weekly/update/{id}", method = RequestMethod.GET)
    public String updateWeek(@PathVariable("id")Integer id, Model model){
        List<Integer> startTimeList = getStartTimeList();
        List<Integer> endTimeList = getEndTimeList();
        List<String> dayNameList = getSevenDays();
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        model.addAttribute("dayNameList", dayNameList);
        model.addAttribute("startTimeList", startTimeList);
        model.addAttribute("endTimeList", endTimeList);
    return "Update";
    }

    @RequestMapping(value = "/planningTool/weekly/update/save", method = RequestMethod.POST)
    public String saveUpdateWeek(@ModelAttribute("event") Event event, RedirectAttributes redirectAttributes) {
        String date = event.getDate();
        List<Event> eventList = eventService.findAll();
        List<Event> filterList = eventList.stream().filter(e -> date.equals(e.getDate())).collect(Collectors.toList());

        try {
            for(Event event1 : filterList){
                if(event1.getStartAt() != event.getStartAt() ){
                    eventService.saveEvent(event);
                }
                redirectAttributes.addFlashAttribute("message", "Event has been added");
            }
        }catch (EventException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "Update";// TODO fix Update.html
        }

        return "redirect:/planningTool/weekly";
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
        for(int i = 7; i <= 18; i++ ){
            timeList.add(i);
        }
        return timeList;
    }
    private List<Integer> getEndTimeList(){
        List<Integer> timeList = new ArrayList<>();
        for(int i = 8; i <= 18; i++ ){
            timeList.add(i);
        }
        return timeList;
    }
    private List<String> getSevenDays(){
        List<String> week = new ArrayList<>();
        String
                mon = "MONDAY",
                tue = "TUESDAY",
                wed = "WEDNESDAY",
                thu =  "THURSDAY",
                fri =  "FRIDAY",
                sat = "SATURDAY",
                sun = "SUNDAY";
        week.add(mon);
        week.add(tue);
        week.add(wed);
        week.add(thu);
        week.add(fri);
        week.add(sat);
        week.add(sun);
        return week;
    }
}
