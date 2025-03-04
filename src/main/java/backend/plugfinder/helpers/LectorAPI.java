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


        for (Object json : data){
            JSONObject charger_json = (JSONObject) json;

            if (!charger_json.getString("tipus_connexi").equals("")) {
                ArrayList<ChargerTypeModel> charger_types = new ArrayList<>();
                String[] types = charger_json.getString("tipus_connexi").split("\\+");
                for (String t : types){
                    if (t.startsWith("2x")) t.replace("2x", "");
                    charger_types.add(charger_type_service.get_charger_by_name(t));
                }

                ChargerModel charger = new ChargerModel();
                charger.setPotency(charger_json.getInt("kw"));
                charger.setOriginal_id(charger_json.getLong("id"));
                charger.setAdress(charger_json.getString("adre_a"));
                charger.setElectric_current(charger_json.getString("ac_dc"));
                charger.setIs_public(true);
                charger.setCompany(charger_json.getString("promotor_gestor"));
                charger.setLatitude(charger_json.getDouble("latitud"));
                charger.setLongitude(charger_json.getDouble("longitud"));
                charger.setTypes(charger_types);
                charger_service.save_charger(charger);

            }
        }
    }
}
