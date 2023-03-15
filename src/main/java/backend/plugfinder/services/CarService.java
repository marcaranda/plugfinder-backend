package backend.plugfinder.services;

import backend.plugfinder.models.CarModel;
import backend.plugfinder.repositories.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarService {
    @Autowired
    CarRepo carRepo;

    public ArrayList<CarModel> getCars(){
        return (ArrayList<CarModel>) carRepo.findAll();
    }

    public CarModel saveCar(CarModel carModel){
        return carRepo.save(carModel);
    }

    public boolean deleteCar(String license){
        try{
            carRepo.deleteById(license);
            return true;
        }
        catch (Exception error){
            return false;
        }
    }
}
