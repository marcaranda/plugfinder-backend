package backend.plugfinder;

import backend.plugfinder.helpersId.CarId;
import backend.plugfinder.helpersId.ModelBrandId;
import backend.plugfinder.models.BrandModel;
import backend.plugfinder.models.CarModel;
import backend.plugfinder.models.ModelBrandModel;
import backend.plugfinder.models.UserModel;
import backend.plugfinder.repositories.BrandRepo;
import backend.plugfinder.repositories.CarRepo;
import backend.plugfinder.repositories.ModelBrandRepo;
import backend.plugfinder.repositories.UserRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class PlugfinderApplication {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CarRepo carRepo;
	@Autowired
	private BrandRepo brandRepo;
	@Autowired
	private ModelBrandRepo modelBrandRepo;

	public static void main(String[] args) {
		SpringApplication.run(PlugfinderApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		UserModel userModel = new UserModel();
		userModel.setNombre_usuario("robert");
		userModel.setNombre_real("Roberto Molina");
		userModel.setTelefono("658941227");
		userModel.setCorreo("roberto@gmail.com");
		userModel.setContrasena("hola");
		userModel.setFecha_nacimiento("07-01-2002");
		userRepo.save(userModel);

		UserModel userModel_2 = new UserModel();
		userModel_2.setNombre_usuario("nico");
		userModel_2.setNombre_real("Nicolas Ramos");
		userModel_2.setTelefono("658941223");
		userModel_2.setCorreo("nico@gmail.com");
		userModel_2.setContrasena("hola");
		userModel_2.setFecha_nacimiento("07-01-2002");
		userRepo.save(userModel_2);

		BrandModel brandModel = new BrandModel();
		brandModel.setName("Tesla");
		brandModel.setKnown(true);
		brandRepo.save(brandModel);

		ModelBrandId modelBrandId = new ModelBrandId("Model X", "Tesla");
		ModelBrandId modelBrandId_2 = new ModelBrandId("Model S", "Tesla");

		ModelBrandModel modelBrandModel = new ModelBrandModel();
		modelBrandModel.setId(modelBrandId);
		modelBrandModel.setKnown(true);
		modelBrandModel.setBrandModel(brandModel);
		modelBrandRepo.save(modelBrandModel);

		ModelBrandModel modelBrandModel_2 = new ModelBrandModel();
		modelBrandModel_2.setId(modelBrandId_2);
		modelBrandModel_2.setKnown(true);
		modelBrandModel_2.setBrandModel(brandModel);
		modelBrandRepo.save(modelBrandModel_2);

		CarId carId = new CarId("0790LFB", userModel.getId_usuario());
		CarId carId_2 = new CarId("0790LFB", userModel_2.getId_usuario());

		CarModel carModel = new CarModel();
		carModel.setId(carId);
		carModel.setAlias("prueba");
		carModel.setAutonomy("400");
		carModel.setUserModel(userModel);
		carModel.setModelBrandModel(modelBrandModel);
		carRepo.save(carModel);

		CarModel carModel_2 = new CarModel();
		carModel_2.setId(carId_2);
		carModel_2.setAlias("prueba");
		carModel_2.setAutonomy("400");
		carModel_2.setUserModel(userModel);
		carModel_2.setModelBrandModel(modelBrandModel);
		carRepo.save(carModel_2);
	}
}
