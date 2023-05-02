package backend.plugfinder.helpers;

import backend.plugfinder.repositories.BrandRepo;
import backend.plugfinder.repositories.ChargerTypeRepo;
import backend.plugfinder.repositories.ModelBrandRepo;
import backend.plugfinder.repositories.UserRepo;
import backend.plugfinder.repositories.entity.BrandEntity;
import backend.plugfinder.repositories.entity.ChargerTypeEntity;
import backend.plugfinder.repositories.entity.ModelBrandEntity;
import backend.plugfinder.repositories.entity.UserEntity;
import backend.plugfinder.services.BrandService;
import backend.plugfinder.services.ChargerTypeService;
import backend.plugfinder.services.ModelBrandService;
import backend.plugfinder.services.UserService;
import backend.plugfinder.services.models.BrandModel;
import backend.plugfinder.services.models.ChargerTypeModel;
import backend.plugfinder.services.models.ModelBrandModel;
import backend.plugfinder.services.models.UserModel;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class LectorBD {
    @Autowired
    private BrandService brand_service;
    @Autowired
    private ModelBrandService model_brand_service;
    @Autowired
    private UserService user_service;
    @Autowired
    private ChargerTypeService charger_type_service;

    public void read_models() throws IOException, OurException {

        // Load the Excel file
        FileInputStream input_stream = new FileInputStream(new File("modelos.xlsx"));

        // Get the workbook instance for XLSX file
        Workbook workbook = WorkbookFactory.create(input_stream);

        // Get the first sheet from the workbook
        Sheet sheet = workbook.getSheetAt(0);

        boolean end = false;

        UserModel user = user_service.find_user_by_id(1L);
        if (user == null) {
            user = new UserModel();
            user.setUsername("admin");
            user.setReal_name("Admin");
            user.setPhone("+34649063779");
            user.setEmail("admin@gmail.com");
            user.setPassword("admin");
            user.setBirth_date("07/01/2002");
            user.setAdmin(true);
            user.setFavorite_chargers(new ArrayList<>());
            user = user_service.user_register(user);
        }

        // Iterate through each row in the sheet
        Iterator<Row> row_iterator = sheet.iterator();
        while (row_iterator.hasNext() && !end) {
            Row row = row_iterator.next();

            if (row.getCell(0).getCellType() == CellType.BLANK){
                end = true;
                break;
            }

            BrandModel brand = brand_service.get_by_id(row.getCell(0).getStringCellValue());
            if (brand == null) {
                brand = new BrandModel();
                brand.setName(row.getCell(0).getStringCellValue());
                brand.setKnown(true);
                brand = brand_service.save_brand(brand);
            }

            ModelBrandId id = new ModelBrandId();
            switch (row.getCell(1).getCellType()){
                case STRING -> id.setName(row.getCell(1).getStringCellValue());
                case NUMERIC -> id.setName(Double.toString(row.getCell(1).getNumericCellValue()));
            }
            id.setBrand_name(brand.getName());
            id.setUser_id(1L);
            id.setAutonomy(Double.toString(row.getCell(2).getNumericCellValue()));

            ArrayList<ChargerTypeModel> chargers_types = new ArrayList<>();
            String[] types = row.getCell(3).getStringCellValue().split(",");
            for (String t: types){
                ChargerTypeModel type = charger_type_service.get_charger_by_name(t);
                if (type == null) {
                    type = new ChargerTypeModel();
                    type.setName(t);
                    type = charger_type_service.save_charger_type(type);
                }
                chargers_types.add(type);
            }

            ModelBrandModel model = new ModelBrandModel();
            model.setId(id);
            model.setKnown(true);
            model.setBrand_model(brand);
            model.setUser_model(user);
            model.setChargers_types(chargers_types);
            model = model_brand_service.save_model(model);
            }

        // Close the input stream and return the list of people
        input_stream.close();
    }
}
