package com.codenotfound.primefaces.model.repository;


import com.codenotfound.primefaces.model.entity.Role;
import com.codenotfound.primefaces.model.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
//    @Query(value = "from Role r join RoleUser ru on ru.roleId = r.id where ru.userId= :userId and ru.isActive =0  ")
//    public Set<Role> findRoleUserByUserIdActive(@Param("userId") Long userId);

    @Query(value = "from Role r inner join RoleUser ru on ru.roleId = r.id where ru.userId= :userId and ru.isActive =0  ")
    public Set<Role> findRoleUserByUserIdActive(@Param("userId") Long userId);

    @Query(value = "from RoleUser ru where ru.userId=:userId and ru.isActive=0 ")
    public List<RoleUser> findRoleUserActiveByUserId(@Param("userId") Long userId);


}