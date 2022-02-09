package com.dogginator.PlanningTool.controller;

import com.dogginator.PlanningTool.model.Users;
import com.dogginator.PlanningTool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;



    @RequestMapping(value = "/planningTool/index", method = RequestMethod.GET)
    public String getHome(){
    return "index";
    } // TODO Create Main Page

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(){
    return "login";
    }// TODO Create a Login Page + Set up admin/Security

    @RequestMapping(value = "/createAccount", method = RequestMethod.GET)
    public String postAccount(Model model){
        model.addAttribute("user", new Users());
        return "CreateAccount"; // TODO Create Account Page + landing page
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public String saveToDB(Model model, Users user, RedirectAttributes ra){
        model.addAttribute("user", user);
        try{
            userService.createUser(user);
            return "DashBoard";
        }catch (ThrowExceptionMessage e){
           ra.addFlashAttribute("message", e.getMessage());
           return "redirect:/createAccount";
        }
    }

    @RequestMapping(value = "/admin/userlist", method = RequestMethod.GET)
    public String adminPage(Model model){
        List<Users> listUsers = userService.getAllUsers();
        model.addAttribute("listUsers", listUsers);
        return "admin"; // TODO Create the admin page
    }
    @RequestMapping(value = "/admin/delete/user/{id}")
    public String removeUser(@PathVariable("id") Integer id, RedirectAttributes ra){
        userService.deleteUser(id);
        return "redirect:/admin/userlist";
    }
}
