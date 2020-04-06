package com.example.springboottest.model.service;

import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.RoleFunction;
import org.springframework.stereotype.Service;

@Service
public interface RoleFunctionService {
    RoleFunction createOrUpdateRoleFunction(RoleFunction roleFunction);

    Boolean delete(RoleFunction roleFunction);

    RoleFunction getbyId(long id);

    RoleFunction save(RoleFunction roleFunction);
    
}
