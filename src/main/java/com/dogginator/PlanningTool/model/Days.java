package com.dogginator.PlanningTool.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


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

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "users_ID")
    private Users users;

    public  Days(){
        super();
    }


    public Days(String day,
                String date,
                String event,
                int time

    ){
        super();
        this.day = day;
        this.date = date;
        this.event = event;
        this.time = time;
    }
}
