package com.codenotfound.primefaces.model.service;

import com.codenotfound.primefaces.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createOrUpdateUser(User user);

    Boolean delete(User user);

    User getbyId(Long id);

    User save(User user);
    
}
