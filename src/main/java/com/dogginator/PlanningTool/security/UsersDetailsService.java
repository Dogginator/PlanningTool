package com.dogginator.PlanningTool.security;


import com.dogginator.PlanningTool.controller.UserRepository;
import com.dogginator.PlanningTool.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName){
        Optional<Users> users = userRepository.findUsesByUserName(userName);

        users.orElseThrow(() -> new UsernameNotFoundException("Input Error: Username not found: " + userName));
        return users.map(UsersDetails::new).get();

    }

}
