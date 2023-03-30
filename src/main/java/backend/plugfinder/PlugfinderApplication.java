package backend.plugfinder;

import backend.plugfinder.helpers.ExcelController;
import backend.plugfinder.models.BrandModel;
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

import java.io.IOException;
import java.util.Optional;

@SpringBootApplication
public class PlugfinderApplication {
	@Autowired
	private ExcelController excel_controller;
	@Autowired
	private UserRepo user_repo;

	public static void main(String[] args) {
		SpringApplication.run(PlugfinderApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() throws IOException {
		Optional<UserModel> user_model = user_repo.findById(1L);
		if (!user_model.isPresent()) {
			excel_controller.read_models();
		}
	}
}
