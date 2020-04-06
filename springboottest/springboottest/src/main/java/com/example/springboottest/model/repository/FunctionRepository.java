package com.example.springboottest.model.repository;


import com.example.springboottest.model.entity.Function;
import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Long> {


    @Query(value =  "from Function s where (:functionName is null or s.functionName like %:functionName%) and (:functionCode is null or s.functionCode like %:functionCode%) and s.status <> 2 ")
    List<Function> getListFunction(@Param("functionName") String functionName  , @Param("functionCode") String functionCode );

    @Query(value =  "from Function s where  s.status <> 2 ")
    List<Function> getListFunctionAll();

    @Query(value =  "from Function s inner join RoleFunction rf on rf.functionId=s.id where rf.isActive <> 1 and  rf.roleId=:roleId and s.status <> 2 ")
    List<Function> getListFunctionActiveByRole(@Param("roleId") Long roleId);

    @Query(value =  "from Function s where  s.status <> 2 and s.id in :funcList")
    Set<Function> finByFuncId(@Param("funcList") List<Long> funcList);

    @Query(value = "from Function f inner join RoleFunction rf on f.id = rf.functionId " +
            "inner join RoleUser ru on ru.roleId = rf.roleId where  ru.userId =:userId ")
    List<Function> getListFuncbyUserId(@Param("userId") Long userId);


}