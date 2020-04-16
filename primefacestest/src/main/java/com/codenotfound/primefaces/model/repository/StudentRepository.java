package com.codenotfound.primefaces.model.repository;


import com.codenotfound.primefaces.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value =  "from Student s where (:phone is null or s.phoneNumber like %:phone%) and (:name is null or s.name like %:name%)")
    List<Student> getListStudent(@Param("phone") String phone, @Param("name") String name);
}