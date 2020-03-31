package com.example.springboottest.model.service.serviceImp;


import com.example.springboottest.model.entity.Function;
import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.repository.FunctionRepository;
import com.example.springboottest.model.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class FunctionServiceImpl implements FunctionService {


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    FunctionRepository functionRepository;
    
    @PersistenceContext
    public EntityManager em;


    @Override
    public Function createOrUpdateFunction(Function function) {
        return functionRepository.save(function);
    }

    @Override
    public Boolean delete(Function function) {
        if(function.getId() > 0 ){
            functionRepository.delete(function);
            return true;
        }else return false;
    }

    @Override
    public Function getbyId(Long id) {
        return functionRepository.findById(id).get();
    }

    @Override
    public Function save(Function function) {
        System.out.println(function.getId()+"  "+ function.getFunctionName());
        return functionRepository.save(function);
    }

   



}