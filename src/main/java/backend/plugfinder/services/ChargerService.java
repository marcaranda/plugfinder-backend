package backend.plugfinder.services;
import backend.plugfinder.repositories.ChargerFiltersRepo;
import backend.plugfinder.repositories.ChargerSpecification;
import backend.plugfinder.repositories.SearchCriteria;
import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.repositories.entity.CarEntity;
import backend.plugfinder.repositories.entity.ChargerEntity;
import backend.plugfinder.services.models.CarModel;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.repositories.ChargerRepo;
import backend.plugfinder.services.models.ChargerTypeModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChargerService {
    @Autowired
    ChargerRepo charger_repo;

    @Autowired
    ChargerFiltersRepo charger_filters;

    @Autowired
    ChargerTypeService charger_type_service;

    @Autowired
    AmazonS3Service amazonS3Service;

    private JpaSpecificationExecutor<ChargerEntity> chargerRepository;

    //https://www.baeldung.com/rest-api-search-language-spring-data-specifications --> seguir esto
    public ArrayList<ChargerModel> buscar_cargadores(Boolean is_public, Double latitude, Double longitude, Integer type, Long real_charge_speed, Long radius) {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerModel> chargers = new ArrayList<>();

        ChargerSpecification spec1 = null;
        ChargerSpecification spec2 = null;
        ChargerSpecification spec3 = null;
        ChargerSpecification spec4 = null;
        ChargerSpecification spec5 = null;
        ChargerSpecification spec6 = null;
        ChargerSpecification spec7 = null;

        if(is_public!=null){
            spec1 = new ChargerSpecification(new SearchCriteria("is_public", ":", is_public));
        }
        if (type!=null) {
            spec2 = new ChargerSpecification(new SearchCriteria("type_id", ":", type));
        }
        if (latitude != null && longitude != null && radius != null) {
            double [] limites = calcularLimites(latitude, longitude, radius);

            spec3 = new ChargerSpecification(new SearchCriteria("longitude", ">", limites[0]));
            spec4 = new ChargerSpecification(new SearchCriteria("longitude", "<", limites[1]));
            spec5 = new ChargerSpecification(new SearchCriteria("latitude", ">", limites[2]));
            spec6 = new ChargerSpecification(new SearchCriteria("latitude", "<", limites[3]));

        }

        if (real_charge_speed!=null) {
            spec7 = new ChargerSpecification(new SearchCriteria("potency", ":", real_charge_speed));
        }

        List<ChargerEntity> results = charger_filters.findAll(Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5).and(spec6).and(spec7));


        results.forEach(elementB -> chargers.add(model_mapper.map(elementB, ChargerModel.class)));
        return chargers;
    }

    public ArrayList<ChargerModel> get_user_chargers(Long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();
            ArrayList<ChargerModel> chargers = new ArrayList<>();

            charger_repo.findAllByUserId(user_id).forEach(elementB -> chargers.add(model_mapper.map(elementB, ChargerModel.class)));
            return chargers;
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    //region Public Methods
//    public ArrayList<ChargerModel> get_chargers(String is_public, Double latitude, Double longitude, String type, Long real_charge_speed, Long radius){
//        ModelMapper model_mapper = new ModelMapper();
//        ArrayList<ChargerModel> chargers = new ArrayList<>();
//
//        if (is_public == null ) {
//            charger_repo.findAll().forEach(elementB -> {
//                //Si la distancia entre el cargador i el punt seleccionat es menor a 5km afegira el carregador a la llista
//                if ((latitude == null && longitude == null) || Haversine(latitude, longitude, elementB.getLocation().getId().getLatitude(), elementB.getLocation().getId().getLongitude(), radius)){
//                    chargers.add(model_mapper.map(elementB, ChargerModel.class));
//                }
//            });
//        }else {
//            charger_repo.findAllByPublic(is_public.equals("true")).forEach(elementB -> {
//                if ((latitude == null && longitude == null) || Haversine(latitude, longitude, elementB.getLocation().getId().getLatitude(), elementB.getLocation().getId().getLongitude(), radius)) {
//                    chargers.add(model_mapper.map(elementB, ChargerModel.class));
//                }
//
//            });
//        }
//
//        return chargers;
//    }


    public ChargerModel save_charger(ChargerModel chargerModel) {
        ModelMapper model_mapper = new ModelMapper();

        ChargerModel charger_saved = model_mapper.map(charger_repo.save(model_mapper.map(chargerModel, ChargerEntity.class)), ChargerModel.class);

        /* Pujem l'imatge pasada en base 64 al bucket s3 de amazon i emmagatzemem la seva url pública al atribut charger_photo del cargador */
        if(chargerModel.getCharger_photo_base64() != null) {
            String public_url_photo = amazonS3Service.upload_file("charger-" + charger_saved.getId_charger(), chargerModel.getCharger_photo_base64());
            charger_saved.setCharger_photo(public_url_photo);
        }

        return model_mapper.map(charger_repo.save(model_mapper.map(charger_saved, ChargerEntity.class)), ChargerModel.class);

    }

    public ChargerModel occupy(ChargerModel chargerModel) {
        ModelMapper model_mapper = new ModelMapper();

        ChargerModel charger = find_charger_by_id(chargerModel.getId_charger());

        if (charger != null) {
            if (charger.isOccupied()){
                throw new RuntimeException("El cargador ya esta ocupado");
            }else{
                charger.setOccupied(true);
            }
            return model_mapper.map(charger_repo.save(model_mapper.map(charger, ChargerEntity.class)), ChargerModel.class);
        }
        else {
            return null;
        }
    }

    public ChargerModel active_charger(Long id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();

        ChargerModel charger = find_charger_by_id(id);
        if (charger != null){
            if (new TokenValidator().validate_id_with_token(charger.getOwner_user().getUser_id()) && !charger.isIs_public()) {
                charger.setActive(!charger.isActive());
                return model_mapper.map(charger_repo.save(model_mapper.map(charger, ChargerEntity.class)), ChargerModel.class);
            } else {
                throw new OurException("El user_id del propietario del cargador es diferente al especificado en el token");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cargador no existe");
        }
    }

    public ChargerModel disoccupy(ChargerModel chargerModel) {
        ModelMapper model_mapper = new ModelMapper();

        ChargerModel charger = find_charger_by_id(chargerModel.getId_charger());

        if (charger != null) {
            charger.setOccupied(false);
            return model_mapper.map(charger_repo.save(model_mapper.map(charger, ChargerEntity.class)), ChargerModel.class);
        }
        else {
            return null;
        }
    }

    public String delete_charger(Long charger_id) throws OurException {
        ChargerModel charger = find_charger_by_id(charger_id);

        if (charger != null) {
            if (new TokenValidator().validate_id_with_token(charger.getOwner_user().getUser_id()) && !charger.isIs_public()) {
                try {
                    charger_repo.deleteById(charger_id);
                    return "Se elimino correctamente el cargador con id " + charger_id;
                } catch (Exception error) {
                    return "No se ha podido eliminar el cargador con id " + charger_id;
                }
            } else {
                throw new OurException("El user_id del propietario del cargador es diferente al especificado en el token");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cargador no existe");
        }
    }

    //region Editar Cargador Privat
    public ChargerModel update_charger(Long charger_id, Double price, String electric_current, Integer potency, ArrayList<Long> chargers_type_id, String photo) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ChargerModel charger_to_be_updated = model_mapper.map(charger_repo.findById(charger_id), ChargerModel.class);

        if (charger_to_be_updated != null) {
            //Si el cargador es privat, llabors es pot actualitzar
            if (!charger_to_be_updated.isIs_public()) {
                if (price != null){
                    charger_to_be_updated.setPrice(price);
                }

                if (electric_current != null){
                    charger_to_be_updated.setElectric_current(electric_current);
                }

                if (potency != null){
                    charger_to_be_updated.setPotency(potency);
                }

                if (chargers_type_id != null){
                    List<ChargerTypeModel> types = new ArrayList<>();
                    for (Long id: chargers_type_id){
                        ChargerTypeModel type = charger_type_service.get_charger_type_by_id(id);
                        if (type != null) types.add(type);
                        else {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El tipo de cargador no existe");
                        }
                    }
                    charger_to_be_updated.setTypes(types);
                }

                //Si la foto del cargador és diferent l'actualitzem
                photo = extractImageBase64(photo);
                if (photo != null) {
                    if (charger_to_be_updated.getCharger_photo() != null) {
                        //Eliminem la foto anterior
                        amazonS3Service.delete_file("charger-" + charger_to_be_updated.getId_charger());
                    }
                    //Guardem la nova foto
                    String public_url_photo = amazonS3Service.upload_file("charger-" + charger_id, photo);
                    charger_to_be_updated.setCharger_photo(public_url_photo);
                }

                //Com la crida al métode save està especificant l'id del cargador, no s'està fent un insert, sinó un update
                return model_mapper.map(charger_repo.save(model_mapper.map(charger_to_be_updated, ChargerEntity.class)), ChargerModel.class);
            } else {
                throw new OurException("No se puede actualizar un cargador público");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cargador no existe");
        }
    }

    /** Actualitza la puntuación del cargador
     * @param charger_id: Id del cargador
     * @param score: Puntuación del cargador
     * @throws OurException: Si el cargador no existeix
     */
    public void update_charger_score(Long charger_id, double score, int num_comments) throws OurException {
        ChargerModel charger = find_charger_by_id(charger_id);

        if (charger != null) {
            charger.setScore(recalculate_score(charger.getScore(), score, num_comments));
            charger_repo.save(new ModelMapper().map(charger, ChargerEntity.class));
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cargador no existe");
        }
    }

    //endregion

    public ChargerModel find_charger_by_id(Long id) {
        ModelMapper model_mapper = new ModelMapper();

        try {
            return model_mapper.map(charger_repo.findById(id), ChargerModel.class);
        }
        catch (MappingException e) {
            return null;
        }
    }
    //endregion

    //region Metodos Privados

    /** Check if the distance between the charger and the point is less than the max distance
     * @param latitude: Latitude of the point
     * @param longitude: Longitude of the point
     * @param latitude_charger: Latitude of the charger
     * @param longitude_charger: Longitude of the charger
     * @param max_distance: Max distance between the charger and the point
     * @return: True if the distance between the charger and the point is less than the max distance
     */
    private boolean Haversine(double latitude, double longitude, double latitude_charger, double longitude_charger, double max_distance) {
        // Radi de la terra en km
        double earth_radius = 6371.0; //(6378 si vui agafar l'equatorial)

        //Caluclem la distància entre l'ubicació especificada i el carregador
        double distance = earth_radius * Math.acos(Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(latitude_charger)) * Math.cos(Math.toRadians(longitude_charger)
                - Math.toRadians(longitude)) + Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(latitude_charger)));

        if(distance <= max_distance){
            return true;
        }
        return false;
    }
    //endregion

    private double[] calcularLimites(double latitud, double longitud, double radio) {
        // Convertir la latitud y longitud a radianes
        double latitudRad = Math.toRadians(latitud);
        double longitudRad = Math.toRadians(longitud);

        // Radio de la Tierra en kilometros
        final double radioTierra = 6371.0;

        // Calcular la distancia en radianes que representa el radio de búsqueda
        double distanciaRad = radio / radioTierra;

        // Calcular los límites de longitud
        double longitudMinRad = longitudRad - distanciaRad / Math.cos(latitudRad);
        double longitudMaxRad = longitudRad + distanciaRad / Math.cos(latitudRad);

        // Calcular los límites de latitud
        double latitudMinRad = latitudRad - distanciaRad;
        double latitudMaxRad = latitudRad + distanciaRad;

        // Convertir los límites de latitud y longitud a grados
        double[] limites = new double[4];
        limites[0] = Math.toDegrees(longitudMinRad); // límite oeste
        limites[1] = Math.toDegrees(longitudMaxRad); // límite este
        limites[2] = Math.toDegrees(latitudMinRad);  // límite sur
        limites[3] = Math.toDegrees(latitudMaxRad);  // límite norte

        return limites;
    }

    /** Recalcula la puntuación del cargador
     * @param score: Puntuación del cargador
     * @return: Puntuación recalculada
     */
    private double recalculate_score(double actual_score, double score, int num_comments) {
        return (actual_score*(num_comments-1) + score)/num_comments;
    }

    /**
     * This method extracts the image from the base64 string.
     * @param fileBase64 - Base64 string.
     * @return String - Image in base64.
     */
    private String extractImageBase64(String fileBase64) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(fileBase64);
            return jsonNode.get("photo").asText();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error while trying to extract the image");
        }
    }
}
