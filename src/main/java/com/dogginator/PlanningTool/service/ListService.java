package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.model.Event;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ListService{
    DateService dateService = new DateService();

    public List<Integer> getStartTimeList(){
        List<Integer> listInt = new ArrayList<>();
        for(int i = 7; i <= 18; i++ ){
            listInt.add(i);
        }
        return listInt;
    }
    public List<Integer> getEndTimeList(){
        List<Integer> listInt = new ArrayList<>();
        for(int i = 8; i <= 18; i++ ){
            listInt.add(i);
        }
        return listInt;
    }
    public List<String> getSevenDays(){
        List<String> week = new ArrayList<>();
        String
                mon = "MONDAY",
                tue = "TUESDAY",
                wed = "WEDNESDAY",
                thu =  "THURSDAY",
                fri =  "FRIDAY",
                sat = "SATURDAY",
                sun = "SUNDAY";
        week.add( mon);
        week.add( tue);
        week.add( wed);
        week.add( thu);
        week.add( fri);
        week.add( sat);
        week.add( sun);
        return week;
    }

    public List<Event> getEventToday( List<Event> eventList){
        String today = LocalDate.now().toString();
        return eventList.stream()
                .filter(event -> today.equals(event.getDate())).collect(Collectors.toList());
    }
    public List<Event> getNextDay(int nextDay, List<Event> eventList){
        return eventList.stream().filter(event -> dateService.addDaysToDate(nextDay).equals(event.getDate())).collect(Collectors.toList());
    }

    public List<Event> checkEventByTime(Event date, List<Event> eventList){
        List<Event> filteredList = eventList.stream().filter(event -> date.getDate().equals(event.getDate())).collect(Collectors.toList());
        return  filteredList.stream().filter(event -> date.getStartAt().equals(event.getStartAt())).collect(Collectors.toList());

    }
    public List<Event> sortList(List<Event> taskList){
        return taskList.stream().sorted(Comparator.comparing(Event::getStartAt)).collect(Collectors.toList());
    }
}
