package backend.plugfinder.services;

import backend.plugfinder.repositories.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    CarRepo carRepo;
}
