package backend.plugfinder;

import backend.plugfinder.models.CarModel;
import backend.plugfinder.models.UserModel;
import backend.plugfinder.repositories.CarRepo;
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

		CarModel carModel = new CarModel();
		carModel.setLicense("0790LFB");
		carModel.setAlias("motomami");
		carModel.setModel_brand("Tesla");
		carModel.setAutonomy("400");
		carModel.setUserModel(userModel);
		carRepo.save(carModel);*/
	}

}
