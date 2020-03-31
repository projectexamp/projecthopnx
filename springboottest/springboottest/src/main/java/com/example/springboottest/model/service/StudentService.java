package com.example.springboottest.model.service;

import com.example.springboottest.model.entity.Student;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student createOrUpdateStudent(Student student);

    Boolean delete(Student student);
    
    Student getbyId(long id);
    
    Student save(Student student);
    
}
