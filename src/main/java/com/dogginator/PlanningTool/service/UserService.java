package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.controller.UserNameException;
import com.dogginator.PlanningTool.model.Users;

import java.util.List;

public interface UserService {
    void createUser(Users users) throws UserNameException;
    Users getUserById(Integer id);
    void deleteUser(Integer id);
    void saveUser(Users users) throws UserNameException;
    List<Users> getAllUsers();
}
