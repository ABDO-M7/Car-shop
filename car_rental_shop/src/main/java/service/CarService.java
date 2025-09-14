package service;

import org.bson.conversions.Bson;

import dao.CarDao;
import model.Car;

public class CarService {
    private  final CarDao carDao;
    
    public CarService(){
        this.carDao = new CarDao();
    }

    public Car findCar(String id){
        return carDao.findCar(id);

    }
    
    public  String addCar(Car car){
        return carDao.addCar(car);
    }
    


    public void showCars(Bson Filter){
        carDao.showCars(Filter);
    }

    public void deleteCar(String id){
        carDao.deleteCar(id);
    }

    public void updateCar(String id, Car car){
        carDao.updateCar(id, car);
    }


}
