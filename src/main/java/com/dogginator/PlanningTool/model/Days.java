package com.dogginator.PlanningTool.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Getter
@Setter
@ToString
@NoArgsConstructor

public class Days {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 15, nullable = false, name ="day" )
    private String day;
    @Column(length = 15, nullable = false, name ="date" )
    private String date;
    @Column(length = 25, nullable = false, name ="event" )
    private String event;
    @Column(length = 5, nullable = false, name ="time" )
    private int time;


    public Days(String day,
                String date,
                String event,
                int time

    ){
        this.day = day;
        this.date = date;
        this.event = event;
        this.time = time;
    }
}
