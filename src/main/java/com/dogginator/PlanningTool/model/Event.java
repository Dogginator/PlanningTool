package com.dogginator.PlanningTool.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@ToString
@Table(catalog = "Event")
@Entity
public class Event {

    @Id
    @GeneratedValue
    private int dayId;

    @Column(length = 15, nullable = false, name ="day" )
    private String day;
    @Column(length = 15, nullable = false, name ="date" )
    private String date;
    @Column(length = 25, nullable = false, name ="event" )
    private String event;
    @Column(length = 1, nullable = false, name ="start at:" )
    private int startAt;
    @Column(length = 1, nullable = false, name ="end at:" )
    private int endAt;

    private boolean thisWeek = false; // TODO hardcoded for now (create an option for user)


    public Event(){
        super();
    }


    public Event(String day,
                 String date,
                 String event,
                 int startAt,
                 int endAt,
                 boolean thisWeek

    ){
        super();
        this.day = day;
        this.date = date;
        this.event = event;
        this.startAt = startAt;
        this.endAt = endAt;
        this.thisWeek = thisWeek;
    }
}
