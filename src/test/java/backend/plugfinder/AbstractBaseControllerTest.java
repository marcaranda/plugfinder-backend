package backend.plugfinder;

import backend.plugfinder.helpers.LectorAPI;
import backend.plugfinder.helpers.LectorBD;
import backend.plugfinder.helpers.LectorPlugfinderAPI;
import backend.plugfinder.repositories.UserRepo;
import backend.plugfinder.services.BrandService;
import backend.plugfinder.services.UserService;
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
    protected LectorAPI lector_api;
    @MockBean
    protected LectorPlugfinderAPI lector_plugfinder_api;
    @MockBean
    private UserRepo user_repository;
}
