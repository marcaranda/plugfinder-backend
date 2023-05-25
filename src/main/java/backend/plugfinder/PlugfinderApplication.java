package backend.plugfinder;

import backend.plugfinder.helpers.LectorAPI;
import backend.plugfinder.helpers.LectorBD;

import backend.plugfinder.helpers.LectorPlugfinderAPI;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.UserService;
import backend.plugfinder.services.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@SpringBootApplication
@EnableScheduling
public class PlugfinderApplication {
	@Autowired
	private LectorBD lector_bd;
	@Autowired
	private UserService user_service;
	@Autowired
	private LectorAPI lector_api;

	@Autowired
	private LectorPlugfinderAPI lector_plugfinder_api;

	public static void main(String[] args) {
		SpringApplication.run(PlugfinderApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() throws IOException, OurException {
		UserModel user_model = user_service.find_user_by_id(1L);
		if (user_model == null) {
			lector_bd.read_models();
			lector_api.read_data_chargers();
		}
		if(user_service.find_user_by_id(2L) == null) {
			lector_plugfinder_api.create_user_api();
		}
	}
}
