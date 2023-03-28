package backend.plugfinder.services;

import backend.plugfinder.models.CarModel;
import backend.plugfinder.repositories.CarRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    CarRepo car_repo;

    //region Public Methods
    public ArrayList<CarModel> get_cars(){
        return (ArrayList<CarModel>) car_repo.findAll();
    }

    public CarModel save_car(CarModel carModel){
        return car_repo.save(carModel);
    }

    @Transactional
    public boolean delete_car(String license, long user_id){
        try{
            car_repo.deleteCarModelById_LicenseAndId_Id(license, user_id);
            return true;
        }
        catch (Exception error){
            return false;
        }
    }

    public Optional<CarModel> get_car_by_id(String license, long user_id){
        return car_repo.findCarModelById_LicenseAndId_Id(license, user_id);
    }

    public ArrayList<CarModel> get_cars_by_user_id(long user_id){
        return car_repo.findCarModelsById_Id(user_id);
    }
    //endregion
}
