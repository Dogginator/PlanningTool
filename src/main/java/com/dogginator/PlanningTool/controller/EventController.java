package com.dogginator.PlanningTool.controller;


import com.dogginator.PlanningTool.service.DateService;
import com.dogginator.PlanningTool.model.Event;
import com.dogginator.PlanningTool.service.EventService;
import com.dogginator.PlanningTool.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EventController {
    DateService dateService = new DateService();
    ListService listService = new ListService();
    String currentDay = LocalDate.now().getDayOfWeek().name();


    @Autowired
    EventService eventService;


    @RequestMapping(value = "/planningTool", method = RequestMethod.GET)
    public String home(Model model){
        List<Event> eventList = eventService.findAll();
        eventService.automaticRemoveOldDatesInDatabase(eventList);
        List<Event> eventToday = listService.sortList(listService.getEventToday(eventList));
        String date = LocalDate.now().toString();
        String day = LocalDate.now().getDayOfWeek().name();
        String dayAndDate = day + ", " + date;
        model.addAttribute("eventToday", eventToday);
        model.addAttribute("dayAndDate", dayAndDate);
        System.out.println(eventToday);
        return "Index";
    }

    @RequestMapping(value = "/updating/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id")Integer id, Model model){
        List<Integer> startTimeList = listService.getStartTimeList();
        List<Integer> endTimeList = listService.getEndTimeList();
        List<String> dayNameList = listService.getSevenDays();
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        model.addAttribute("dayNameList", dayNameList);
        model.addAttribute("startTimeList", startTimeList);
        model.addAttribute("endTimeList", endTimeList);
        return "Updating";
    }
    @RequestMapping(value = "/updating/save", method = RequestMethod.POST)
    public String saveUpdating(@ModelAttribute("event") Event event, RedirectAttributes redirectAttributes){
        String date = dateService.planningCheck(event);
        event.setDate(date);
        try {
            eventService.saveEvent(event);
            redirectAttributes.addFlashAttribute("message", "Event has been added");

        }catch (EventException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/planningTool";
        }
        if(currentDay.equals(event.getDay())){
            return "redirect:/planningTool";
        }else if(!currentDay.equals(event.getDay())){
            return "redirect:/planningTool/weekly";
        }else{
            return "Updating";
        }
    }

    @GetMapping("/delete/{id}")
    public String removeDayInIndex(@PathVariable("id")Integer id ){
        eventService.deleteDay(id);
        return "redirect:/planningTool";
    }

    @RequestMapping(value = "/planningTool/planning", method = RequestMethod.GET)
    public String planning(Model model){
        List<Integer> startTimeList = listService.getStartTimeList();
        List<Integer> endTimeList = listService.getEndTimeList();
        List<String> dayNameList = listService.getSevenDays();
        model.addAttribute("event", new Event());
        model.addAttribute("dayNameList", dayNameList);
        model.addAttribute("startTimeList", startTimeList);
        model.addAttribute("endTimeList", endTimeList);
        return "Planning";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("event")  Event event, Model model,  RedirectAttributes redirectAttributes){
        model.addAttribute("event", event);
        String date = dateService.planningCheck(event);
        event.setDate(date);
            try {
                eventService.createEvent(event);
                redirectAttributes.addFlashAttribute("message", "Event has been added");
            }catch (EventException e){
                redirectAttributes.addFlashAttribute("message", e.getMessage());
                return "redirect:/planningTool";
            }
            if(event.getDay().equals(currentDay)){
                return "redirect:/planningTool";
            }else if(!event.getDay().equals(currentDay)){
                return "redirect:/planningTool/weekly";
            }
            return "Index";

    }

    @RequestMapping(value = "/planningTool/weekly", method = RequestMethod.GET)
    public String week(Model model){
        List<Event> firstDay, secondDay, thirdDay, fourthDay, fifthDay, sixthDay, seventhDay;
        List<Event> eventList = eventService.findAll();
        firstDay = listService.sortList(listService.getNextDay(1, eventList));
        model.addAttribute("firstDay", firstDay);
        secondDay = listService.sortList(listService.getNextDay(2, eventList));
        model.addAttribute("secondDay", secondDay);
        thirdDay = listService.sortList(listService.getNextDay(3, eventList));
        model.addAttribute("thirdDay", thirdDay);
        fourthDay = listService.sortList(listService.getNextDay(4, eventList));
        model.addAttribute("fourthDay", fourthDay);
        fifthDay = listService.sortList(listService.getNextDay(5, eventList));
        model.addAttribute("fifthDay", fifthDay);
        sixthDay = listService.sortList(listService.getNextDay(6, eventList));
        model.addAttribute("sixthDay", sixthDay);
        seventhDay = listService.sortList(listService.getNextDay(7, eventList));
        model.addAttribute("seventhDay", seventhDay);
        return "Weekly";
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateWeek(@PathVariable("id")Integer id, Model model){
        List<Integer> startTimeList = listService.getStartTimeList();
        List<Integer> endTimeList = listService.getEndTimeList();
        List<String> dayNameList = listService.getSevenDays();
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        model.addAttribute("dayNameList", dayNameList);
        model.addAttribute("startTimeList", startTimeList);
        model.addAttribute("endTimeList", endTimeList);
    return "Update";
    }

    @RequestMapping(value = "/update/save", method = RequestMethod.POST)
    public String saveUpdateWeek(@ModelAttribute("event") Event event, RedirectAttributes redirectAttributes) {
        String date = dateService.planningCheck(event);
        event.setDate(date);
        try {
            eventService.saveEvent(event);
            redirectAttributes.addFlashAttribute("message", "Event has been added");

        }catch (EventException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/planningTool/weekly";
        }
        if(currentDay.equals(event.getDay())){
            return "redirect:/planningTool";
        }else if(!currentDay.equals(event.getDay())){
            return "redirect:/planningTool/weekly";
        }else {
            return "Weekly";
        }
    }



    @GetMapping("/planningTool/remove/plan/{id}")
    public String removeDay(@PathVariable("id")Integer id ){
        eventService.deleteDay(id);
        return "redirect:/planningTool/weekly";
    }

}
