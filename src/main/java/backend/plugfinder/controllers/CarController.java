package backend.plugfinder.controllers;

import backend.plugfinder.repositories.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {
    @Autowired
    CarRepo carRepo;
}
