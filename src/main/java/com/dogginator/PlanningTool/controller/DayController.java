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

@Controller
public class DayController {
private Event day;

    @Autowired
    EventService eventService;
    DateService dateService;

    @RequestMapping(value = "/planningTool/dashboard", method = RequestMethod.GET)
    public String homePage(Model model){
        automaticRemoveOldDatesInDatabase();

        return "DashBoard";
    }

    @RequestMapping(value = "/planningTool/planning", method = RequestMethod.POST)
    public String postDay(Model model){
        model.addAttribute("day", new Event());
        return "createDay";// TODO SET UP createDay
    }
    @RequestMapping(value = "(/planningTool/dashboard", method = RequestMethod.POST)
    public String saveDayToDB(Model model, Event day){
        model.addAttribute("day", day);
        return "dashboard"; // TODO SET UP a dashboard
    }

    @RequestMapping(value = "/planningTool/daily", method = RequestMethod.GET)
    public String getCurrentDay(Model model){
        String today = LocalDate.now().toString();
            List<Event> listDay = eventService.findAllEventOnDay(today);
            model.addAttribute("listDay", listDay);
        return "daily";// TODO SET UP daily
    }
    @RequestMapping(value = "/planningTool/weekly", method = RequestMethod.GET) // TODO WIP set up a week
    public String getCurrentWeek(Model model, Event day, DateService dateService){
        day.setDay(LocalDate.now().getDayOfWeek().name());
        List<String> week = dateService.correctWeek();
        List<Event> firstDay, secondDay, thirdDay, fourthDay, fifthDay, sixthDay, seventhDay;
        for(int i = 0; i < week.size(); ++i){
           switch (i){
               case 0 -> {
                    firstDay =  eventService.findAllEventOnDay(dateService.planningCheck(day.isThisWeek()));
                    model.addAttribute("fistDay", firstDay);
               }
               case 1 -> {
                   secondDay = eventService.findAllEventOnDay(dateService.planningCheck(day.isThisWeek()));
                   model.addAttribute("secondDay", secondDay);
               }
               case 2-> {
                   thirdDay = eventService.findAllEventOnDay(dateService.planningCheck(day.isThisWeek()));
                   model.addAttribute("thirdDay", thirdDay);
               }
               case 3 -> {
                   fourthDay = eventService.findAllEventOnDay(dateService.planningCheck(false));
                   model.addAttribute("fourthDay", fourthDay);
               }
               case 4 -> {
                   fifthDay = eventService.findAllEventOnDay(dateService.planningCheck(false));
                   model.addAttribute("fifthDay", fifthDay);
               }
               case 5 -> {
                   sixthDay = eventService.findAllEventOnDay(dateService.planningCheck(false));
                   model.addAttribute("sixthDay", sixthDay);
               }
               case 6 -> {
                   seventhDay = eventService.findAllEventOnDay(dateService.planningCheck(false));
                   model.addAttribute("seventhDay", seventhDay);
               }
           }
        }
        return "weekly";// TODO set up weekly and look in to how to do more and less tables to display in frontend
    }

    @RequestMapping(value = "/planningTool/week", method = RequestMethod.GET)
    public void updateDay(Model model){
        //List<Event> listWeek = eventService.findTotalWeek();// TODO how to set up a Week?
    }
    @RequestMapping(value = "/planningTool/remove/plan/{id}", method = RequestMethod.DELETE)
    public String removeDay(@PathVariable("id")Integer id ){
        eventService.deleteDay(id);
        return "dashboard"; // TODO set up an auto delete method here ones the day has passed
    }

    private void automaticRemoveOldDatesInDatabase(){
        List<Event> removalDays = eventService.findAllEventOnDay(dateService.removeDaysToDate(1));
        if(removalDays.size() != 0){
            for (Event event : removalDays){
                int currentID = event.getDayId();
                eventService.deleteDay(currentID);
            }
        }
    }
    private List<Event> automaticGetCurrentWeek(){

    }
}
