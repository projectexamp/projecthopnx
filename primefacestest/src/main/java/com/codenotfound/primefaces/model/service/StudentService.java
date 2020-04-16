package com.codenotfound.primefaces.model.service;

import com.codenotfound.primefaces.model.entity.Student;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    Student createOrUpdateStudent(Student student);

    Boolean delete(Student student);
    
    Student getbyId(long id);
    
    Student save(Student student);
    
}
