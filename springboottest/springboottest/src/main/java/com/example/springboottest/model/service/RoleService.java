package com.example.springboottest.model.service;

import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.Student;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role createOrUpdateRole(Role role);

    Boolean delete(Role role);

    Role getbyId(long id);

    Role save(Role role);
    
}
