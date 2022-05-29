package com.dogginator.PlanningTool.service;
import com.dogginator.PlanningTool.model.Event;


import java.time.LocalDate;


public class DateService {
    private final String
            mon = "MONDAY",
            tue = "TUESDAY",
            wed = "WEDNESDAY",
            thu =  "THURSDAY",
            fri =  "FRIDAY",
            sat = "SATURDAY",
            sun = "SUNDAY";
    private String plannedDate;
    private final String currentDay = LocalDate.now().getDayOfWeek().name();

    public String planningCheck(Event event){ //Basic logic for Saving Events
        if(event.getDay().equalsIgnoreCase(currentDay)){
            boolean checkWeek = event.isThisWeek();
            if(checkWeek == true){// if its next week on the same day or not
                plannedDate = addDaysToDate(7);// next week
            }else {
                plannedDate = LocalDate.now().toString();
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
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(days);
        return plannedDate = localDate.toString();
    }

    public String removeDaysToDate(int day){
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(day);
        return plannedDate = localDate.toString();
    }
}
