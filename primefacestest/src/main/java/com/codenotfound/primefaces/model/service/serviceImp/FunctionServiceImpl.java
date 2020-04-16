package com.codenotfound.primefaces.model.service.serviceImp;


import com.codenotfound.primefaces.model.entity.Function;
import com.codenotfound.primefaces.model.repository.FunctionRepository;
import com.codenotfound.primefaces.model.service.FunctionService;
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
        return functionRepository.findOne(id);
    }

    @Override
    public Function save(Function function) {
        System.out.println(function.getId()+"  "+ function.getFunctionName());
        return functionRepository.save(function);
    }

   



}