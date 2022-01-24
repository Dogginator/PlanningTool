package com.dogginator.PlanningTool.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity(name = "Users")
public class Users {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 25, nullable = false, name ="fist_Name" )
    private String firstName;
    @Column(length = 25, nullable = false, name ="last_Name" )
    private String lastName;
    @Column(length = 25, nullable = false, unique = true, name ="email" )
    private String email;
    @Column(length = 25, nullable = false, unique = true, name ="user_Name" )
    private String userName;
    @Column(length = 15, nullable = false, name ="password" )
    private String password;
    private String rePassword;
    private String oldPassword;

    private String secret;
    @Column(length = 10, nullable = false, name ="roles" )
    private String roles;


    public Users(String firstName, String lastName, String email, String userName, String password, String secret, String roles ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.secret = secret;
        this.roles = roles;
    }
}
