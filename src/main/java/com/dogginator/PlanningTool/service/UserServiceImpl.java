package com.dogginator.PlanningTool.service;

import com.dogginator.PlanningTool.controller.UserNameException;
import com.dogginator.PlanningTool.controller.UserRepository;
import com.dogginator.PlanningTool.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public void createUser(Users user) throws UserNameException {
        Optional<Users> u = repository.findUsesByUserName(user.getUserName());
        if(u.isPresent()){
            throw new UserNameException("User name Taken");
        }
        repository.save(user);
    }

    @Override
    public Users getUserById(Integer id){return repository.getById(id);}

    @Override
    public void deleteUser(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Users> getAllUsers() {
        return repository.findAll();
    }

}
