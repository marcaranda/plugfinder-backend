package backend.plugfinder;

import backend.plugfinder.helpers.LectorAPI;
import backend.plugfinder.helpers.LectorBD;
import backend.plugfinder.helpers.LectorPlugfinderAPI;
import backend.plugfinder.repositories.UserRepo;
import backend.plugfinder.services.*;
import backend.plugfinder.services.models.ModelBrandModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class AbstractBaseControllerTest {
    @MockBean
    protected LectorBD lector_bd;
    @MockBean
    protected UserService user_service;
    @MockBean
    protected BrandService brand_service;
    @MockBean
    protected ModelBrandService model_brand_service;
    @MockBean
    protected ChargerTypeService charger_type_service;
    @MockBean
    protected CarService car_service;
    @MockBean
    protected LectorAPI lector_api;
    @MockBean
    protected LectorPlugfinderAPI lector_plugfinder_api;
    @MockBean
    private UserRepo user_repository;
}
