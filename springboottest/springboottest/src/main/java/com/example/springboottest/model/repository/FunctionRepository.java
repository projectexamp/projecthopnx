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

}