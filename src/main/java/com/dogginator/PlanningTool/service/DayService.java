package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.model.Days;

import java.util.List;

public interface DayService {
    List<Days> findAllEventOnDay(String date);
    List<Days> findTotalWeek(String id);
    void deleteDay(Integer id);
}
