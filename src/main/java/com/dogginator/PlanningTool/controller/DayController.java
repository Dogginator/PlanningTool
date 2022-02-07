package com.dogginator.PlanningTool.controller;


import com.dogginator.PlanningTool.model.Days;
import com.dogginator.PlanningTool.service.DayService;
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
private Days day;
    @Autowired
    DayService dayService;

    @RequestMapping(value = "/user/planning", method = RequestMethod.GET)
    public String postDay(Model model){
        model.addAttribute("day", new Days());
        return "createDay";// TODO SET UP createDay
    }
    @RequestMapping(value = "(/user/dashboard", method = RequestMethod.POST)
    public String saveDayToDB(Model model, Days day){
        model.addAttribute("day", day);
        return "dashboard";
    }

    @RequestMapping(value = "/user/daily", method = RequestMethod.GET)
    public String getCurrentDay(Model model){
        String today = LocalDate.now().toString();
            List<Days> listDay = dayService.findAllEventOnDay(today);
            model.addAttribute("listDay", listDay);
        return "daily";// TODO SET UP daily
    }

    @RequestMapping(value = "/user/week", method = RequestMethod.GET)
    public void getCurrentWeek(Model model){
        //List<Days> listWeek = dayService.findTotalWeek();// TODO how to set up a Week?
    }
    @RequestMapping(value = "/user/remove/plan/{id}")
    public String removeDay(@PathVariable("id")Integer id ){
        dayService.deleteDay(id);
        return "week";
    }

}
