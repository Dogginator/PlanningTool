package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.controller.DayRepository;
import com.dogginator.PlanningTool.model.Days;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DayServiceImpl implements DayService {

    @Autowired
    DayRepository dayRepo;

    @Override
    public List<Days> findAllEventOnDay(String date){return dayRepo.findAll(date);}
}
