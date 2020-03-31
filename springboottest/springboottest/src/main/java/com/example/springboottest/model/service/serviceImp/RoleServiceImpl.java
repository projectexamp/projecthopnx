package com.example.springboottest.model.service.serviceImp;


import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.Student;
import com.example.springboottest.model.repository.RoleRepository;
import com.example.springboottest.model.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    RoleRepository roleRepository;
    
    @PersistenceContext
    public EntityManager em;



    @Override
    public Role createOrUpdateRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Boolean delete(Role role) {
        if(role.getId() > 0 ){
            roleRepository.delete(role);
            return true;
        }else return false;
    }

    public Role getbyId(long id) {
       return roleRepository.findById(id).get();
    }

    @Override
    public Role save(Role role) {
        System.out.println(role.getId()+"  "+ role.getRoleName());
        return roleRepository.save(role);
    }

//    public void delete(long id) {
//        functionRepository.delete(id);
//    }
   



}