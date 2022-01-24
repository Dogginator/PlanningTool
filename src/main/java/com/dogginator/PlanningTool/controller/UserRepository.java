package com.dogginator.PlanningTool.controller;


import com.dogginator.PlanningTool.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findById(Integer integer);
    Optional<Users> findUsesByUserName(String userName);
}
