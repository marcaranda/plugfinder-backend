package backend.plugfinder.services;

import backend.plugfinder.helpers.OurException;
import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.services.models.MessageModel;
import backend.plugfinder.services.models.UserModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ChatService {
    @Autowired
    UserService user_service;

    ArrayList<Long> active_user = new ArrayList<>();

    public String send_message(MessageModel message) throws OurException {
        if (new TokenValidator().validate_id_with_token(message.getSource_id())) {
            UserModel source_user = user_service.find_user_by_id(message.getSource_id());
            if (source_user == null) throw new OurException("El source user no existe");
            UserModel target_user = user_service.find_user_by_id(message.getTarget_id());
            if (target_user == null) throw new OurException("El target user no existe");

            RestTemplate restTemplate = new RestTemplate();

            // Crear el cuerpo de la solicitud
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("message", message.getMessage());
            requestBody.add("sourceID", source_user.getEmail());
            requestBody.add("targetID", target_user.getEmail());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> response = null;

            String apiUrl = "https://api-xat-pes.onrender.com/api/message";
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            if (active_user.contains(target_user.getUser_id())) {
                 restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
            }

            apiUrl = "https://api-xat-pes.onrender.com/api/messages";
            requestBody.add("time", LocalDateTime.now().toString());
            requestEntity = new HttpEntity<>(requestBody, headers);
            response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
            else {
                throw new OurException("Error al enviar el mensaje");
            }
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public ArrayList<MessageModel> get_messages(long source_id, long target_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(source_id)) {
            UserModel source_user = user_service.find_user_by_id(source_id);
            if (source_user == null) throw new OurException("El source user no existe");
            UserModel target_user = user_service.find_user_by_id(target_id);
            if (target_user == null) throw new OurException("El target user no existe");

            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://api-xat-pes.onrender.com/api/messages?emailsource=" + source_user.getEmail() + "&emaildest=" + target_user.getEmail();

            // Realizar la solicitud GET
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();

                ArrayList<MessageModel> messages = new ArrayList<>();

                try{
                    JSONArray json = new JSONArray(responseBody);

                    for (int i = 0; i < json.length(); ++i){
                        JSONObject object = json.getJSONObject(i);

                        MessageModel message = new MessageModel(object.getString("message"), object.getString("sourceID"), object.getString("targetID"), object.getString("created_at"));
                        if (object.getString("sourceID").equals(source_user.getEmail())){
                            message.setSource_id(source_id);
                            message.setTarget_id(target_id);
                        }
                        else {
                            message.setSource_id(target_id);
                            message.setTarget_id(source_id);
                        }
                        messages.add(message);
                    }
                    return messages;
                }
                catch (JSONException e) {
                    throw new OurException("Error al obtener los mensajes");
                }
            } else {
                throw new OurException("Error al obtener los mensajes");
            }
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public String signin(long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            UserModel source_user = user_service.find_user_by_id(user_id);
            if (source_user == null) throw new OurException("El user no existe");

            active_user.add(user_id);

            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://api-xat-pes.onrender.com/api/signin";

            // Crear el cuerpo de la solicitud
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("id", source_user.getEmail());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
            else {
                throw new OurException("Error al enviar el mensaje");
            }
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public String disconnect(long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            UserModel source_user = user_service.find_user_by_id(user_id);
            if (source_user == null) throw new OurException("El user no existe");

            active_user.remove(user_id);

            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://api-xat-pes.onrender.com/api/disconnect";

            // Crear el cuerpo de la solicitud
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("id", source_user.getEmail());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
            else {
                throw new OurException("Error al enviar el mensaje");
            }
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }
}
