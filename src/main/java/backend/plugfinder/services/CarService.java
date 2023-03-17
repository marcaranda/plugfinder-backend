package backend.plugfinder.services;

import backend.plugfinder.helpersId.CarId;
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
    CarRepo carRepo;

    //region Public Methods
    public ArrayList<CarModel> getCars(){
        return (ArrayList<CarModel>) carRepo.findAll();
    }

    public CarModel saveCar(CarModel carModel){
        return carRepo.save(carModel);
    }

    @Transactional
    public boolean deleteCar(String license, long user_id){
        try{
            carRepo.deleteCarModelById_LicenseAndId_Id(license, user_id);
            return true;
        }
        catch (Exception error){
            return false;
        }
    }

    public Optional<CarModel> getCarById(String license, long user_id){
        return carRepo.findCarModelById_LicenseAndId_Id(license, user_id);
    }

    public ArrayList<CarModel> getCarsByUserId(long user_id){
        return carRepo.findCarModelsById_Id(user_id);
    }
    //endregion
}
