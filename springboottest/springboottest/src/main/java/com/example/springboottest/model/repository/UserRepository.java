package com.example.springboottest.model.repository;



import com.example.springboottest.model.entity.Student;
import com.example.springboottest.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "from User u where u.status <> 2 and u.username =:username")
    public Optional<User> findByUsername(@Param("username") String username);

    @Query(value =  "from User s where (:username is null or s.username like %:username%) and (:fullname is null or s.fullname like %:fullname%) and s.status <> 2 ")
    List<User> getListUser(@Param("username") String username  , @Param("fullname") String fullname );
}