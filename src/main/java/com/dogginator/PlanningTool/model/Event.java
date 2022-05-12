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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer dayId;

    @Column(length = 15, nullable = false, name ="day" )
    private String day;
    @Column(length = 15, nullable = false, name ="date" )
    private String date;
    @Column(length = 25, nullable = false, name ="eventPlan")
    private String eventPlan;
    @Column(length = 1, nullable = false, name ="start_at")
    private Integer startAt;
    @Column(length = 1, nullable = false, name ="end_at" )
    private Integer endAt;

    private boolean thisWeek;


    public Event(){
        super();
    }


    public Event(String day,
                 String date,
                 String eventPlan,
                 int startAt,
                 int endAt,
                 boolean thisWeek

    ){
        super();
        this.day = day;
        this.date = date;
        this.eventPlan = eventPlan;
        this.startAt = startAt;
        this.endAt = endAt;
        this.thisWeek = thisWeek;
    }
}
