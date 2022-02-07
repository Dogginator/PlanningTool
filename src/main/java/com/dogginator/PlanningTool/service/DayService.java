package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.model.Days;

import java.util.List;

public interface DayService {
    List<Days> findAllEventOnDay(String day);
}
