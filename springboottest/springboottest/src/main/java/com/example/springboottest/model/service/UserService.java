package com.example.springboottest.model.service;

import com.example.springboottest.model.entity.Student;
import com.example.springboottest.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createOrUpdateUser(User user);

    Boolean delete(User user);

    User getbyId(Long id);

    User save(User user);
    
}
