package com.example.springboottest.model.service;

import com.example.springboottest.model.entity.Function;
import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.Student;
import org.springframework.stereotype.Service;

@Service
public interface FunctionService {
    Function createOrUpdateFunction(Function function);

    Boolean delete(Function function);

    Function getbyId(Long id);

    Function save(Function function);
    
}
