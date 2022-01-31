package com.dogginator.PlanningTool.controller;

import com.dogginator.PlanningTool.model.Days;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DayRepository extends JpaRepository<Days, Integer> {
    List<Days> findAll(String date);
}
