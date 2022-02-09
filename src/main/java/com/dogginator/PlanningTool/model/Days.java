package com.dogginator.PlanningTool.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

// TODO See if @ManyToOne is whats needed and that it works (test)
@Getter
@Setter
@ToString
@Table(catalog = "Days")
@Entity
public class Days  {

    @Id
    @GeneratedValue
    private int dayId;

    @Column(length = 15, nullable = false, name ="day" )
    private String day;
    @Column(length = 15, nullable = false, name ="date" )
    private String date;
    @Column(length = 25, nullable = false, name ="event" )
    private String event;
    @Column(length = 5, nullable = false, name ="time" )
    private int time;

    private boolean thisWeek = false; // TODO hardcoded for now (create an option for user)

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "users_ID")
    private Users users;

    public  Days(){
        super();
    }


    public Days(String day,
                String date,
                String event,
                int time,
                boolean thisWeek

    ){
        super();
        this.day = day;
        this.date = date;
        this.event = event;
        this.time = time;
        this.thisWeek = thisWeek;
    }
}
