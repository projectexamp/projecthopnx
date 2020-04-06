package com.example.springboottest.model.repository;



import com.example.springboottest.model.entity.Function;
import com.example.springboottest.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value =  "from Role s where (:roleName is null or s.roleName like %:roleName%) and (:roleCode is null or s.roleCode like %:roleCode%) and s.status <> 2 ")
    List<Role> getListRole(@Param("roleName") String roleName  , @Param("roleCode") String roleCode );

    @Query(value =  "from Role s where  s.status <> 2 ")
    List<Role> getListRoleAll();

    @Query(value =  "from Role r inner join RoleUser ru on r.id=ru.roleId where ru.isActive = 0 and  ru.userId=:userId and r.status <> 2 ")
    List<Role> getListRoleActiveByUser(@Param("userId") Long userId);

    @Query(value =  "from Role s where  s.status <> 2 and s.id in :roleList")
    Set<Role> finByRoleId(@Param("roleList") List<Long> roleList);

}