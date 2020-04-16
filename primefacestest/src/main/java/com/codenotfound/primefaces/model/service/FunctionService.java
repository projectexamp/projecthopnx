package com.codenotfound.primefaces.model.service;


import com.codenotfound.primefaces.model.entity.Function;
import org.springframework.stereotype.Service;

@Service
public interface FunctionService {
    Function createOrUpdateFunction(Function function);

    Boolean delete(Function function);

    Function getbyId(Long id);

    Function save(Function function);
    
}
