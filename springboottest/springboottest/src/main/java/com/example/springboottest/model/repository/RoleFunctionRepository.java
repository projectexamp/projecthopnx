package com.example.springboottest.model.repository;



import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.RoleFunction;
import com.example.springboottest.model.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleFunctionRepository extends JpaRepository<RoleFunction, Long> {
//    @Query(value = "from Role r join RoleUser ru on ru.roleId = r.id where ru.userId=:userId and ru.isActive =1 ")
//    public Set<Role> findRoleUserByUserIdActive(Long userId);
    @Query(value = "from RoleFunction rf where rf.roleId=:roleId and rf.isActive=0 ")
    public List<RoleFunction> findRoleFunctionByRoleId(@Param("roleId")Long roleId);

    @Modifying
    @Query("update RoleFunction rf set rf.isActive= 1  where rf.roleId=:roleId and rf.functionId in :listInActive ")
    void InActiveRoleFunction(@Param("roleId")Long roleId , @Param("listInActive") List<Long> listInActive);

}