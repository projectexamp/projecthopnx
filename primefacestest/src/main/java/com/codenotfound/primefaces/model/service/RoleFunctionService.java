package com.codenotfound.primefaces.model.service;

import com.codenotfound.primefaces.model.entity.RoleFunction;
import org.springframework.stereotype.Service;

@Service
public interface RoleFunctionService {
    RoleFunction createOrUpdateRoleFunction(RoleFunction roleFunction);

    Boolean delete(RoleFunction roleFunction);

    RoleFunction getbyId(long id);

    RoleFunction save(RoleFunction roleFunction);
    
}
