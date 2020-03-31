package com.example.springboottest.model.service.serviceImp;


import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.Student;
import com.example.springboottest.model.entity.User;
import com.example.springboottest.model.repository.UserRepository;
import com.example.springboottest.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    UserRepository userRepository;
    
    @PersistenceContext
    public EntityManager em;


    @Override
    public User createOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(User user) {
        if(user.getId() > 0 ){
            userRepository.delete(user);
            return true;
        }else return false;
    }

    @Override
    public User getbyId(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User save(User user) {
        System.out.println(user.getId()+"  "+ user.getUsername());
        return userRepository.save(user);
    }



}