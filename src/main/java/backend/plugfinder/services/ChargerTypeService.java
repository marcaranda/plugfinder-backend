package backend.plugfinder.services;


import backend.plugfinder.repositories.ChargerTypeRepo;
import backend.plugfinder.repositories.entity.ChargerTypeEntity;
import backend.plugfinder.services.models.ChargerTypeModel;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChargerTypeService {
    @Autowired
    ChargerTypeRepo charger_type_repo;

    public ArrayList<ChargerTypeModel> get_chargers_types() {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerTypeModel> chargers_types = new ArrayList<>();

        charger_type_repo.findAll().forEach(elementB -> chargers_types.add(model_mapper.map(elementB, ChargerTypeModel.class)));
        return chargers_types;
    }

    public ChargerTypeModel get_charger_type_by_id(long id) {
        ModelMapper model_mapper = new ModelMapper();

        try {
            return model_mapper.map(charger_type_repo.findById(id), ChargerTypeModel.class);
        }
        catch (MappingException e) {
            return null;
        }
    }

    public ChargerTypeModel save_charger_type(ChargerTypeModel charger_type_model) {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(charger_type_repo.save(model_mapper.map(charger_type_model, ChargerTypeEntity.class)), ChargerTypeModel.class);
    }

    public ChargerTypeModel get_charger_by_name(String name) {
        ModelMapper model_mapper = new ModelMapper();
        if (charger_type_repo.findByName(name).isPresent()) return model_mapper.map(charger_type_repo.findByName(name).get(), ChargerTypeModel.class);
        else return null;
    }

    //region Public Methods

    //endregion
}
