package backend.plugfinder;

import backend.plugfinder.helpers.ModelBrandId;
import backend.plugfinder.models.*;
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
		/*UserModel userModel = new UserModel();
		userModel.setNombre_usuario("robert");
		userModel.setNombre_real("Roberto Molina");
		userModel.setTelefono("658941227");
		userModel.setCorreo("roberto@gmail.com");
		userModel.setContrasena("hola");
		userModel.setFecha_nacimiento("07-01-2002");
		userRepo.save(userModel);

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

		CarModel carModel = new CarModel();
		carModel.setLicense("0790LFB");
		carModel.setAlias("motomami");
		carModel.setAutonomy("400");
		carModel.setUserModel(userModel);
		carModel.setModelBrandModel(modelBrandModel);
		carRepo.save(carModel);*/
	}
}
