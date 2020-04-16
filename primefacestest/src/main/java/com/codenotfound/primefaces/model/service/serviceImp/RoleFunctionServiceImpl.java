package com.codenotfound.primefaces.model.service.serviceImp;


import com.codenotfound.primefaces.model.entity.RoleFunction;
import com.codenotfound.primefaces.model.repository.RoleFunctionRepository;
import com.codenotfound.primefaces.model.service.RoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class RoleFunctionServiceImpl implements RoleFunctionService {


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    RoleFunctionRepository roleFunctionRepository;
    
    @PersistenceContext
    public EntityManager em;


    @Override
    public RoleFunction createOrUpdateRoleFunction(RoleFunction roleFunction) {
        return roleFunctionRepository.save(roleFunction);
    }

    @Override
    public Boolean delete(RoleFunction roleFunction) {
        if(roleFunction.getId() > 0 ){
            roleFunctionRepository.delete(roleFunction);
            return true;
        }else return false;
    }

    public RoleFunction getbyId(long id) {
       return roleFunctionRepository.findOne(id);
    }

    @Override
    public RoleFunction save(RoleFunction roleFunction) {
        System.out.println(roleFunction.getId()+"  "+ roleFunction.getFunctionId());
        return roleFunctionRepository.save(roleFunction);
    }



//    public void delete(long id) {
//        functionRepository.delete(id);
//    }
   



}