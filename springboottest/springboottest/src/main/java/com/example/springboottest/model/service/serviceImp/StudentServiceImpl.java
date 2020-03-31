package com.example.springboottest.model.service.serviceImp;


import com.example.springboottest.model.entity.Student;
import com.example.springboottest.model.repository.StudentRepository;
import com.example.springboottest.model.service.StudentService;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    StudentRepository userRepository;
    
    @PersistenceContext
    public EntityManager em;
    public Student createOrUpdateStudent(Student student) {
        return userRepository.save(student);
    }


    public Boolean delete(Student student) {
        if(student.getID() > 0 ){
           userRepository.delete(student);
           return true;
        }else return false;
        
    }

    public Student getbyId(long id) {
       return userRepository.findById(id).get();
    }
    
//    public void delete(long id) {
//        functionRepository.delete(id);
//    }
   
    public Student save(Student student) {
        System.out.println(student.getID()+"  "+ student.getName());
       return userRepository.save(student);
    }



}