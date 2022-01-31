package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.controller.ThrowExceptionMessage;
import com.dogginator.PlanningTool.model.Users;

import java.util.List;

public interface UserService {
    void createUser(Users users) throws ThrowExceptionMessage;
    Users getUserById(Integer id);
    void deleteUser(Integer id);
    List<Users> getAllUsers();
}
