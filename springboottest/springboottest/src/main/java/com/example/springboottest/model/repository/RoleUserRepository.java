package com.example.springboottest.model.repository;



import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.RoleFunction;
import com.example.springboottest.model.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
    @Query(value = "from Role r join RoleUser ru on ru.roleId = r.id where ru.userId=:userId and ru.isActive =0  ")
    public Set<Role> findRoleUserByUserIdActive(Long userId);

    @Query(value = "from RoleUser ru where ru.userId=:userId and ru.isActive=0 ")
    public List<RoleUser> findRoleUserActiveByUserId(@Param("userId")Long userId);


}