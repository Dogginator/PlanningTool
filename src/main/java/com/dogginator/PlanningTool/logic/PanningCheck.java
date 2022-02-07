package com.dogginator.PlanningTool.logic;

import com.dogginator.PlanningTool.model.Days;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PanningCheck {
    private Days day;
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
    private final List<String> week = new ArrayList<String>();
    private  List<String>  dates = new ArrayList<String>();

    public String planningCheck(Boolean checkWeek){ //Basic logic for Saving Events
        if(day.getDay().equalsIgnoreCase(currentDay)){
            if(checkWeek){
                plannedDate = addDaysToDate(7);
            }else {
                plannedDate = addDaysToDate(0);
            }
            return plannedDate;
        }
        else{
            switch (currentDay){
                case mon -> plannedDate = caseCheck(tue, wed, thu, fri, sat, sun);
                case tue -> plannedDate = caseCheck(wed, thu, fri, sat, sun, mon);
                case wed -> plannedDate = caseCheck(thu, fri, sat, sun, mon, tue);
                case thu -> plannedDate = caseCheck(fri, sat, sun, mon, tue, wed);
                case fri -> plannedDate = caseCheck(sat, sun, mon, tue, wed, thu);
                case sat -> plannedDate = caseCheck(sun, mon, tue, wed, thu, fri);
                case sun -> plannedDate = caseCheck(mon, tue, wed, thu, fri, sat);
            }
        }
        return plannedDate;
    }

    public List<String> correctWeek(){ //Basic logic for Tables
        switch (currentDay){
            case mon -> {// current week
                week.add("Tuesday");
                week.add("Wednesday");
                week.add("Thursday");
                week.add("Friday");
                week.add("Saturday");
                week.add("Sunday");
                dates = weekDates(week);
            }
            case tue -> {
                week.add("Wednesday");
                week.add("Thursday");
                week.add("Friday");
                week.add("Saturday");
                week.add("Sunday");
                dates = weekDates(week);
            }
            case wed -> {
                week.add("Thursday");
                week.add("Friday");
                week.add("Saturday");
                week.add("Sunday");
                dates = weekDates(week);
            }
            case thu -> {
                week.add("Friday");
                week.add("Saturday");
                week.add("Sunday");
                dates = weekDates(week);
            }
            case fri -> {
                week.add("Saturday");
                week.add("Sunday");
                weekDates(week);
            }
            case sat -> {
                week.add("Sunday");
                weekDates(week);
            }
            case sun -> { // next week
                week.add("monday");
                week.add("Tuesday");
                week.add("Wednesday");
                week.add("Thursday");
                week.add("Friday");
                week.add("Saturday");
                week.add("Sunday");
                weekDates(week);
            }
            }
            return dates;
        }

    private List<String> weekDates(List<String> checkThis){// For getting the Right date for data
    for(int i = 1; i < checkThis.size(); ++i){
        Date currentDate1 = new Date();
        LocalDateTime localDateTime1 = currentDate1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime1 = localDateTime1.plusDays(i);
        Date newDate1 = Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());
        String plannedDate1 = dateFormat.format(newDate1);
        dates.add(plannedDate1);
    }
    return dates;
    }

    private  String caseCheck(String one, String two, String three, String four, String five, String six){ // add days to current date
        if(day.getDay().equalsIgnoreCase(one)) {
            plannedDate = addDaysToDate(1);
        }else if(day.getDay().equalsIgnoreCase(two)){
            plannedDate = addDaysToDate(2);
        }else if(day.getDay().equalsIgnoreCase(three)){
            plannedDate = addDaysToDate(3);
        }else if(day.getDay().equalsIgnoreCase(four)){
            plannedDate = addDaysToDate(4);
        }else if(day.getDay().equalsIgnoreCase(five)){
            plannedDate = addDaysToDate(5);
        }else if(day.getDay().equalsIgnoreCase(six)){
            plannedDate = addDaysToDate(6);
        }
        return plannedDate;
    }

    private String addDaysToDate(int days){ // If a plan is a head of the current date
        localDateTime = localDateTime.plusDays(days);
        Date newDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        plannedDate = dateFormat.format(newDate);
        return plannedDate;
    }
}
