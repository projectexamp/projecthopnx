package com.codenotfound.primefaces.model.service;


import com.codenotfound.primefaces.model.entity.Role;
import com.codenotfound.primefaces.model.repository.RoleRepository;
import com.codenotfound.primefaces.model.repository.RoleUserRepository;
import com.codenotfound.primefaces.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private RoleUserRepository roleUserRepository ;

    @Autowired
    private RoleRepository roleRepository ;

    @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
     //Buscar el usuario con el repositorio y si no existe lanzar una exepcion
     com.codenotfound.primefaces.model.entity.User appUser =
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