package backend.plugfinder.services;
import backend.plugfinder.repositories.ChargerFiltersRepo;
import backend.plugfinder.repositories.ChargerSpecification;
import backend.plugfinder.repositories.SearchCriteria;
import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.repositories.entity.ChargerEntity;
import backend.plugfinder.services.models.CarModel;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.repositories.ChargerRepo;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChargerService {
    @Autowired
    ChargerRepo charger_repo;

    @Autowired
    ChargerFiltersRepo charger_filters;

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

        /* Pujem l'imatge pasada en base 64 al bucket s3 de amazon i emmagatzemem la seva url pública al atribut charger_photo del cargador */
        if(chargerModel.getCharger_photo_base64() != null) {
            String public_url_photo = amazonS3Service.upload_file("charger-" + chargerModel.getId_charger(), chargerModel.getCharger_photo_base64());
            chargerModel.setCharger_photo_base64(public_url_photo);
        }

        return model_mapper.map(charger_repo.save(model_mapper.map(chargerModel, ChargerEntity.class)), ChargerModel.class);

    }

    public ChargerModel new_charge(ChargerModel chargerModel) {
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


    public ChargerModel end_charge(ChargerModel chargerModel) {
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

    public boolean delete_charger(long id) {
        try{
            charger_repo.deleteById(id);
            return true;
        }
        catch (Exception error){
            return false;
        }
    }

    //region Editar Cargador Privat
    public ChargerModel update_charger(ChargerModel charger_model) throws OurException {
        if(new TokenValidator().validate_id_with_token(charger_model.getOwner_user().getUser_id())) {
            ModelMapper model_mapper = new ModelMapper();
            ChargerModel charger_to_be_updated = model_mapper.map(charger_repo.findById(charger_model.getId_charger()), ChargerModel.class);

            //Si el cargador es privat, llabors es pot actualitzar
            if(!charger_to_be_updated.isIs_public()) {
                //Si la foto del cargador és diferent l'actualitzem
                if(charger_model.getCharger_photo_base64() != null) {
                    if(charger_to_be_updated.getCharger_photo() != null) {
                        //Eliminem la foto anterior
                        amazonS3Service.delete_file("charger-" + charger_to_be_updated.getId_charger());
                    }
                    //Guardem la nova foto
                    String public_url_photo = amazonS3Service.upload_file("charger-" + charger_model.getId_charger(), charger_model.getCharger_photo_base64());
                    charger_model.setCharger_photo(public_url_photo);
                }

                //Com la crida al métode save està especificant l'id del cargador, no s'està fent un insert, sinó un update
                return model_mapper.map(charger_repo.save(model_mapper.map(charger_model, ChargerEntity.class)), ChargerModel.class);
            }
            else {
                throw new OurException("No se puede actualizar un cargador público");
            }
        }
        else {
            throw new OurException("El user_id del propietario del cargador es diferente al especificado en el token");
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



}
