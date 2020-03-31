package com.example.springboottest.controller;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController {


    @GetMapping({"/dangnhap","/login"})
    public String login() {

        return "admin/login";
    }

    @GetMapping("/menu")
    public String menu() {
        return "admin/menu";
    }


    @GetMapping("/admin")
    public String admin(){
//        System.out.println("dn");
        return  "admin/index" ;
    }

    @GetMapping("/admin/function-manager")
    public String managerFunction(){
        return  "admin/functionmanager" ;
    }

    @GetMapping("/admin/role-manager")
    public String managerRole(){
        return  "admin/rolemanager" ;
    }

    @GetMapping("/user")
    public String user(){
        return  "user/testuser" ;
    }

    @GetMapping("/logout")
    public String logout(){
        return  "admin/login" ;
    }

}