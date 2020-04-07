package com.example.springboottest.controller;



import com.example.springboottest.model.entity.Function;
import com.example.springboottest.model.repository.FunctionRepository;
import com.example.springboottest.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;


@Controller
public class MainController {

    @Autowired
    private FunctionRepository functionRepository ;
    @Autowired
    private UserRepository userRepository ;

    @GetMapping({"/dangnhap","/login"})
    public String login() {

        return "admin/login";
    }

    @GetMapping("/menu")
    public String menu() {
        return "admin/menu";
    }


    @GetMapping("/admin/user_manager")
    public String admin(ModelMap model){
//        System.out.println("dn");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String CurrentUserName = authentication.getName() ;
        com.example.springboottest.model.entity.User appUser =
                userRepository.findByUsername(CurrentUserName).orElseThrow(() -> new UsernameNotFoundException("No existe user_id on tbl_role_user"));

        Long userId = appUser.getId() ;
        Set<Function> listFunc = functionRepository.getListFuncbyUserId(userId);

        model.addAttribute("listFunc" ,listFunc) ;
        return  "admin/index" ;
    }

    @GetMapping("/admin/function_manager")
    public String managerFunction(ModelMap model){
//        model.getAttribute();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String CurrentUserName = authentication.getName() ;
        com.example.springboottest.model.entity.User appUser =
                userRepository.findByUsername(CurrentUserName).orElseThrow(() -> new UsernameNotFoundException("No existe user_id on tbl_role_user"));

        Long userId = appUser.getId() ;
        Set<Function> listFunc = functionRepository.getListFuncbyUserId(userId);

        model.addAttribute("listFunc" ,listFunc) ;

        return  "admin/functionmanager" ;
    }

    @GetMapping("/admin/role_manager")
    public String managerRole(ModelMap model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String CurrentUserName = authentication.getName() ;
        com.example.springboottest.model.entity.User appUser =
                userRepository.findByUsername(CurrentUserName).orElseThrow(() -> new UsernameNotFoundException("No existe user_id on tbl_role_user"));

        Long userId = appUser.getId() ;
        Set<Function> listFunc = functionRepository.getListFuncbyUserId(userId);

        model.addAttribute("listFunc" ,listFunc) ;
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