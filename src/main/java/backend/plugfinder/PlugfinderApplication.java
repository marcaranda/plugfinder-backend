package backend.plugfinder;

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
		/*ArrayList<UserModel> users = (ArrayList<UserModel>) userRepo.findAll();
		UserModel userModel = users.get(0);

		ArrayList<ModelBrandModel> models = (ArrayList<ModelBrandModel>) modelBrandRepo.findAll();
		ModelBrandModel modelBrandModel = models.get(0);

		CarId carId = new CarId("0790LFB", userModel.getUser_id());

		CarModel carModel = new CarModel();
		carModel.setId(carId);
		carModel.setAlias("prueba");
		carModel.setAutonomy("400");
		carModel.setUserModel(userModel);
		carModel.setModelBrandModel(modelBrandModel);
		carRepo.save(carModel);*/
	}
}
