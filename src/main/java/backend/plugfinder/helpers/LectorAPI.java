package backend.plugfinder.helpers;

import backend.plugfinder.services.ChargerService;
import backend.plugfinder.services.ChargerTypeService;
import backend.plugfinder.services.LocationService;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.ChargerTypeModel;
import backend.plugfinder.services.models.LocationModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.ArrayList;


@Component
public class LectorAPI {
    @Autowired
    LocationService location_service;
    @Autowired
    ChargerTypeService charger_type_service;
    @Autowired
    ChargerService charger_service;

    @Scheduled(cron = "0 0 0 * * MON") // Ejecutar a las 00:00 los lunes
    public void read_data_chargers(){
        RestTemplate rest_template = new RestTemplate();
        String api_url = "https://analisi.transparenciacatalunya.cat/resource/tb2m-m33b.json";
        ResponseEntity<String> response = rest_template.getForEntity(api_url, String.class);
        JSONArray data = new JSONArray(response.getBody());

        long x = 1;

        for (Object json : data){
            JSONObject charger_json = (JSONObject) json;

            if (!charger_json.getString("tipus_connexi").equals("")) {
                LocationId location_id = new LocationId(charger_json.getDouble("latitud"), charger_json.getDouble("longitud"));
                LocationModel location = new LocationModel();
                location.setId(location_id);
                location = location_service.save_location(location);

                ArrayList<ChargerTypeModel> charger_types = new ArrayList<>();
                String[] types = charger_json.getString("tipus_connexi").split("\\+");
                for (String t : types){
                    if (t.startsWith("2x")) t.replace("2x", "");
                    charger_types.add(charger_type_service.get_charger_by_name(t));
                }

                ChargerModel charger = new ChargerModel();
                charger.setId_charger(x);
                charger.setAlias_charger("alias");
                charger.setState("state");
                charger.setPrice_x_kw(0);
                charger.setMax_time_charging(Time.valueOf("00:10:50"));
                charger.setIs_public(true);
                charger.setCompany(charger_json.getString("promotor_gestor"));
                charger.setLocation(location);
                charger.setTypes(charger_types);
                charger_service.save_charger(charger);

                ++x;
            }
        }
    }
}
