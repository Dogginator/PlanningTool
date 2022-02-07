package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.controller.DayRepository;
import com.dogginator.PlanningTool.model.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DayServiceImpl implements DayService {

    @Autowired
    DayRepository dayRepo;

    @Override
    public List<Days> findAllEventOnDay(String date){return dayRepo.findAll(date);}
    @Override
    public List<Days> findTotalWeek(String id){return dayRepo.findAll(id);}
    @Override
    public void deleteDay(Integer id){dayRepo.deleteById(id);}

}
