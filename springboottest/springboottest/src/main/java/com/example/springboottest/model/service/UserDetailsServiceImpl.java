package com.example.springboottest.model.service;


import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.repository.RoleUserRepository;
import com.example.springboottest.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleUserRepository roleUserRepository ;
	
    @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
     //Buscar el usuario con el repositorio y si no existe lanzar una exepcion
     com.example.springboottest.model.entity.User appUser =
                 userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No existe user_id on tbl_role_user"));
		
    //Mapear nuestra lista de Authority con la de spring security 
    List grantList = new ArrayList();
    Set<Role> roleSet = roleUserRepository.findRoleUserByUserIdActive(appUser.getId()) ;
    for (Role role: roleSet) {
        // ROLE_USER, ROLE_ADMIN,..
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleCode());
            grantList.add(grantedAuthority);
    }
		
    //Crear El objeto UserDetails que va a ir en sesion y retornarlo.
    UserDetails user = (UserDetails) new User(appUser.getUsername(), appUser.getPassword(), grantList);
         return user;
    }
}