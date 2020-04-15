package com.codenotfound.primefaces.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import com.codenotfound.primefaces.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import com.codenotfound.primefaces.model.Car;
import com.codenotfound.primefaces.repository.CarRepository;

@Named("dtPaginatorView")
@ViewScoped
public class CarsView implements Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private CarService carService ;

  @Autowired
  private CarRepository carRepository;

  private List<Car> cars;

  @PostConstruct
  public void init() {
    cars = carService.getListCar();
  }

  public List<Car> getCars() {
    return cars;
  }
}
