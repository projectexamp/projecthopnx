package com.codenotfound.primefaces.model.service.serviceImp;


import com.codenotfound.primefaces.model.entity.Student;
import com.codenotfound.primefaces.model.repository.StudentRepository;
import com.codenotfound.primefaces.model.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
       return userRepository.findOne(id);
    }
    
//    public void delete(long id) {
//        functionRepository.delete(id);
//    }
   
    public Student save(Student student) {
        System.out.println(student.getID()+"  "+ student.getName());
       return userRepository.save(student);
    }



}