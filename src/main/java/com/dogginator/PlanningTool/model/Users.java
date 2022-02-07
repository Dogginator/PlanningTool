package com.dogginator.PlanningTool.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(catalog = "Users")
@Entity
public class Users {

    @Id
    @GeneratedValue
    private int id;


    @Column(length = 25, nullable = false, unique = true, name ="email" )
    private String email;
    @Column(length = 25, nullable = false, unique = true, name ="user_Name" )
    private String userName;
    @Column(length = 15, nullable = false, name ="password" )
    private String password;
    private String secret;
    @Column(length = 10, nullable = false, name ="roles" )
    private String roles;

    @OneToMany(targetEntity = Days.class, cascade = CascadeType.ALL)
    private List days;


    public Users(String email, String userName, String password, String secret, String roles, List days ){
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.secret = secret;
        this.roles = roles;
        this.days = days;
    }
}
