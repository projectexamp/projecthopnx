package com.codenotfound.primefaces.model.service;

import com.codenotfound.primefaces.model.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role createOrUpdateRole(Role role);

    Boolean delete(Role role);

    Role getbyId(long id);

    Role save(Role role);
    
}
