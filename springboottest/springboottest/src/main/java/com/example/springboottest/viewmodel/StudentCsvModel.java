package com.example.springboottest.viewmodel;

import com.opencsv.bean.CsvBindByName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentCsvModel {

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String phoneNumber ;

    @CsvBindByName
    private String birthDate ;

    @CsvBindByName
    private String addr ;      // ten nguoi nhan


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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public StudentCsvModel() {
    }


}
