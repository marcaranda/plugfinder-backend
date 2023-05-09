package backend.plugfinder.services;
import backend.plugfinder.repositories.ChargerFiltersRepo;
import backend.plugfinder.repositories.ChargerSpecification;
import backend.plugfinder.repositories.SearchCriteria;
import backend.plugfinder.repositories.entity.ChargerEntity;
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
