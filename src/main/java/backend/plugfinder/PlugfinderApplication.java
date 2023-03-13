package backend.plugfinder;

import backend.plugfinder.models.UserModel;
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

	public static void main(String[] args) {
		SpringApplication.run(PlugfinderApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		/*UserModel newUser = new UserModel();
		newUser.setNombre_usuario("arnau");
		newUser.setNombre_real("ArnauArmen");
		newUser.setTelefono(645022330);
		newUser.setCorreo("rarna@gmail.com");
		newUser.setContrasena("hola");
		newUser.setFecha_nacimiento("13-12-2002");
		this.userRepo.save(newUser);*/
	}

}