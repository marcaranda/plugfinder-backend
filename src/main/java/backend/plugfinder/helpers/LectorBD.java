package backend.plugfinder.helpers;

import backend.plugfinder.repositories.BrandRepo;
import backend.plugfinder.repositories.ModelBrandRepo;
import backend.plugfinder.repositories.UserRepo;
import backend.plugfinder.repositories.entity.BrandEntity;
import backend.plugfinder.repositories.entity.ChargerTypeEntity;
import backend.plugfinder.repositories.entity.ModelBrandEntity;
import backend.plugfinder.repositories.entity.UserEntity;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class LectorBD {
    @Autowired
    private BrandRepo brand_repo;
    @Autowired
    private ModelBrandRepo model_brand_repo;
    @Autowired
    private UserRepo user_repo;

    public List<ModelBrandEntity> read_models() throws IOException {
        List<ModelBrandEntity> models = new ArrayList<>();

        // Load the Excel file
        FileInputStream input_stream = new FileInputStream(new File("modelos.xlsx"));

        // Get the workbook instance for XLSX file
        Workbook workbook = WorkbookFactory.create(input_stream);

        // Get the first sheet from the workbook
        Sheet sheet = workbook.getSheetAt(0);

        boolean end = false;

        Optional<UserEntity> user_model = user_repo.findById(1L);
        UserEntity user = new UserEntity();
        if (user_model.isPresent()) {
            user = user_model.get();
        }
        else {
            user.setUsername("admin");
            user.setReal_name("Admin");
            user.setPhone("+34649063779");
            user.setEmail("admin@gmail.com");
            user.setPassword("admin");
            user.setBirth_date("07/01/2002");
            user.setAdmin(true);
            user_repo.save(user);
        }

        // Iterate through each row in the sheet
        Iterator<Row> row_iterator = sheet.iterator();
        while (row_iterator.hasNext() && !end) {
            Row row = row_iterator.next();

            if (row.getCell(0).getCellType() == CellType.BLANK){
                end = true;
                break;
            }

            Optional<BrandEntity> brand_model = brand_repo.findById(row.getCell(0).getStringCellValue());
            BrandEntity brand = new BrandEntity();
            if (brand_model.isPresent()) {
                brand = brand_model.get();
            }
            else {
                brand.setName(row.getCell(0).getStringCellValue());
                brand.setKnown(true);
                brand_repo.save(brand);
            }

                ModelBrandId id = new ModelBrandId();
                switch (row.getCell(1).getCellType()){
                    case STRING -> id.setName(row.getCell(1).getStringCellValue());
                    case NUMERIC -> id.setName(Double.toString(row.getCell(1).getNumericCellValue()));
                }
                id.setBrand_name(brand.getName());
                id.setUser_id(1L);
                id.setAutonomy(Double.toString(row.getCell(2).getNumericCellValue()));

                ModelBrandEntity model = new ModelBrandEntity();
                ArrayList<ChargerTypeEntity> types = new ArrayList<>();
                model.setId(id);
                model.setKnown(true);
                model.setBrand_model(brand);
                model.setUser_model(user);
                model.setChargers_types(types);
                model_brand_repo.save(model);

                models.add(model);
            }

        // Close the input stream and return the list of people
        input_stream.close();
        return models;
    }
}
