package com.codenotfound.primefaces.model.entity;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long ID;

    @Column(name = "name")
    private String name;   

    @Column(name="phone_number")
    private String phoneNumber ;

    @Column(name="birth_date")
    private Date birthDate ;  

    @Column(name ="address")
    private String addr ;      // ten nguoi nhan

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    
    public Student() {
    }
    
    public Student(String name, String phoneNumber, String addr, String birthDate) throws ParseException {
    	this.name = name;
    	this.phoneNumber = phoneNumber;
    	this.addr = addr;
    	if(birthDate!=null)
    		this.birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
    	
    }
}
