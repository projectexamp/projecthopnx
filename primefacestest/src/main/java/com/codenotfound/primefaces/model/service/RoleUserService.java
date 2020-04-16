package com.codenotfound.primefaces.model.service;

import com.codenotfound.primefaces.model.entity.RoleUser;

import org.springframework.stereotype.Service;

@Service
public interface RoleUserService {
    RoleUser createOrUpdateRoleUser(RoleUser roleUser);

    Boolean delete(RoleUser roleUser);

    RoleUser getbyId(long id);

    RoleUser save(RoleUser roleUser);
    
}
