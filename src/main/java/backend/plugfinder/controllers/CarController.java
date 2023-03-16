package backend.plugfinder.controllers;

import backend.plugfinder.models.CarModel;
import backend.plugfinder.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    CarService carService;

    //region Get Methods
    @GetMapping
    public ArrayList<CarModel> getCars(){
        return carService.getCars();
    }

    //http://localhost:8080/cars/byUser?id= 'USER_ID'
    @GetMapping(path = "/byUser")
    public ArrayList<CarModel> getCarsByUserId(@RequestParam("id") long id){
        return carService.getCarsByUserId(id);
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public CarModel saveCar(@RequestBody CarModel carModel) throws SQLException {
        /* Comprobación validez matrícula */
        /*if (!validateLicense(carModel.getId().getLicense())){
            throw new SQLException("La matrícula no es válida");
        }*/
        /* Comprobación validez autonomía */
        /*if (!validateAutonomy(carModel.getAutonomy())){
            throw new SQLException("La autonomía no es válida");
        }*/
        return carService.saveCar(carModel);
    }
    //endregion

    //region Delete Methods
    @DeleteMapping(path = "/{id}")
    public String deleteCar(@PathVariable("id") String license){
        if (carService.deleteCar(license)){
            return "Se elimino correctamente el coche con matricula " + license;
        }
        else{
            return "No se ha podido eliminar el coche con matricula " + license;
        }
    }
    //endregion

    //region Private Methods
    private boolean validateLicense(String license){
        String patron = "^\\[0-9]{4}[A-Z]{3}$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(license);
        return matcher.matches();
    }

    private boolean validateAutonomy(String autonomy){
        String patron = "^{2,4}[0-9]$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(autonomy);
        return matcher.matches();
    }
    //endregion
}
