package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.model.Event;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class DateService {
    private final String
            mon = "MONDAY",
            tue = "TUESDAY",
            wed = "WEDNESDAY",
            thu =  "THURSDAY",
            fri =  "FRIDAY",
            sat = "SATURDAY",
            sun = "SUNDAY";
    private final String date = "yyyy-MM-dd";
    private final DateFormat dateFormat = new SimpleDateFormat(date);
    private final Date currentDate = new Date();
    private LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    private String plannedDate;
    private final String currentDay = LocalDate.now().getDayOfWeek().name();

    public String planningCheck(Event event){ //Basic logic for Saving Events
        if(event.getDay().equalsIgnoreCase(currentDay)){
            boolean checkWeek = event.isThisWeek();
            if(checkWeek){// if its next week on the same day or not
                plannedDate = addDaysToDate(7);// next week
            }else {
                plannedDate = addDaysToDate(0);//current week
            }
            return plannedDate;
        }
        else{
            switch (currentDay){
                case mon -> plannedDate = caseCheck( event, tue, wed, thu, fri, sat, sun);
                case tue -> plannedDate = caseCheck( event, wed, thu, fri, sat, sun, mon);
                case wed -> plannedDate = caseCheck( event, thu, fri, sat, sun, mon, tue);
                case thu -> plannedDate = caseCheck( event, fri, sat, sun, mon, tue, wed);
                case fri -> plannedDate = caseCheck( event, sat, sun, mon, tue, wed, thu);
                case sat -> plannedDate = caseCheck( event, sun, mon, tue, wed, thu, fri);
                case sun -> plannedDate = caseCheck( event, mon, tue, wed, thu, fri, sat);
            }
        }
        return plannedDate;
    }

    private  String caseCheck( Event event, String one, String two, String three, String four, String five, String six){ // add days to current date
        if(event.getDay().equalsIgnoreCase(one)) {
            plannedDate = addDaysToDate(1);
        }else if(event.getDay().equalsIgnoreCase(two)){
            plannedDate = addDaysToDate(2);
        }else if(event.getDay().equalsIgnoreCase(three)){
            plannedDate = addDaysToDate(3);
        }else if(event.getDay().equalsIgnoreCase(four)){
            plannedDate = addDaysToDate(4);
        }else if(event.getDay().equalsIgnoreCase(five)){
            plannedDate = addDaysToDate(5);
        }else if(event.getDay().equalsIgnoreCase(six)){
            plannedDate = addDaysToDate(6);
        }
        return plannedDate;
    }

    public String addDaysToDate(int days){ // If a plan is a head of the current date
        localDateTime = localDateTime.plusDays(days);
        Date newDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        plannedDate = dateFormat.format(newDate);
        return plannedDate;
    }

    public String removeDaysToDate(int day){
        localDateTime = localDateTime.minusDays(day);
        Date newDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        plannedDate = dateFormat.format(newDate);
        return plannedDate;
    }
    public LocalDateTime getToday(){
        Date currentDate = new Date();
        return currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
