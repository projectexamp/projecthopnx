package com.example.springboottest.model.repository;



import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleFunctionRepository extends JpaRepository<RoleUser, Long> {
//    @Query(value = "from Role r join RoleUser ru on ru.roleId = r.id where ru.userId=:userId and ru.isActive =1 ")
//    public Set<Role> findRoleUserByUserIdActive(Long userId);
}