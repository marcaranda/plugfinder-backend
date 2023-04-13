package backend.plugfinder;

import backend.plugfinder.helpers.ExcelController;

import backend.plugfinder.services.UserService;
import backend.plugfinder.services.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;


@SpringBootApplication
public class PlugfinderApplication {
	@Autowired
	private ExcelController excel_controller;
	@Autowired
	private UserService user_service;

	public static void main(String[] args) {
		SpringApplication.run(PlugfinderApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() throws IOException {
		UserModel user_model = user_service.find_user_by_id(1L);
		if (user_model == null) {
			excel_controller.read_models();
		}
	}
}
