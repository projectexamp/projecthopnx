package com.codenotfound.primefaces.service;

import com.codenotfound.primefaces.model.Car;
import com.codenotfound.primefaces.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository ;

    public List<Car> getListCar() {
        List<Car> list = carRepository.findAll();

        return list;
    }
}
