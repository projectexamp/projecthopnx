package com.example.springboottest.model.service.serviceImp;


import com.example.springboottest.model.entity.RoleFunction;
import com.example.springboottest.model.entity.RoleUser;
import com.example.springboottest.model.repository.RoleUserRepository;
import com.example.springboottest.model.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class RoleUserServiceImpl implements RoleUserService {


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    RoleUserRepository roleUserRepository;
    
    @PersistenceContext
    public EntityManager em;




    @Override
    public RoleUser createOrUpdateRoleUser(RoleUser roleUser) {
        return roleUserRepository.save(roleUser);
    }

    @Override
    public Boolean delete(RoleUser roleUser) {
        if(roleUser.getId() > 0 ){
            roleUserRepository.delete(roleUser);
            return true;
        }else return false;
    }

    public RoleUser getbyId(long id) {
       return roleUserRepository.findById(id).get();
    }

    @Override
    public RoleUser save(RoleUser roleUser) {
        System.out.println(roleUser.getId()+"  "+ roleUser.getRoleId());
        return roleUserRepository.save(roleUser);
    }




//    public void delete(long id) {
//        functionRepository.delete(id);
//    }
   



}