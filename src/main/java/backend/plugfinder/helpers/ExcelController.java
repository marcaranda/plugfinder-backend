package backend.plugfinder.helpers;

import backend.plugfinder.models.BrandModel;
import backend.plugfinder.models.ModelBrandModel;
import backend.plugfinder.models.UserModel;
import backend.plugfinder.repositories.BrandRepo;
import backend.plugfinder.repositories.ModelBrandRepo;
import backend.plugfinder.repositories.UserRepo;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class ExcelController {
    @Autowired
    private BrandRepo brand_repo;
    @Autowired
    private ModelBrandRepo model_brand_repo;
    @Autowired
    private UserRepo user_repo;

    @GetMapping(path = "/read_brands")
    public List<BrandModel> read_brands() throws IOException {
        List<BrandModel> brands = new ArrayList<>();

        // Load the Excel file
        FileInputStream input_stream = new FileInputStream(new File("/home/marc/Downloads/marcas.xlsx"));

        // Get the workbook instance for XLSX file
        Workbook workbook = WorkbookFactory.create(input_stream);

        // Get the first sheet from the workbook
        Sheet sheet = workbook.getSheetAt(0);

        boolean end = false;
        // Iterate through each row in the sheet
        Iterator<Row> row_iterator = sheet.iterator();
        while (row_iterator.hasNext() && !end) {
            Row row = row_iterator.next();

            if (row.getCell(0).getCellType() == CellType.BLANK){
                end = true;
                break;
            }

            BrandModel brand = new BrandModel();
            brand.setName(row.getCell(0).getStringCellValue());
            brand.setKnown(true);
            brand_repo.save(brand);

            brands.add(brand);
        }
        // Close the input stream and return the list of people
        input_stream.close();
        return brands;
    }

    @GetMapping(path = "/read_models")
    public List<ModelBrandModel> read_models() throws IOException {
        List<ModelBrandModel> models = new ArrayList<>();

        // Load the Excel file
        FileInputStream input_stream = new FileInputStream(new File("/home/marc/Downloads/modelos.xlsx"));

        // Get the workbook instance for XLSX file
        Workbook workbook = WorkbookFactory.create(input_stream);

        // Get the first sheet from the workbook
        Sheet sheet = workbook.getSheetAt(0);

        boolean end = false;

        Optional<UserModel> user_model = user_repo.findById(1L);
        UserModel user = new UserModel();
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

            Optional<BrandModel> brand_model = brand_repo.findById(row.getCell(0).getStringCellValue());
            BrandModel brand = new BrandModel();
            if (brand_model.isPresent()) {
                brand = brand_model.get();
            }
            else {
                brand.setName(row.getCell(0).getStringCellValue());
                brand.setKnown(true);
                brand_repo.save(brand);
            }

                ModelBrandId id = new ModelBrandId();
                id.setName(row.getCell(1).getStringCellValue());
                id.setBrand_name(brand.getName());
                id.setUser_id(1L);
                id.setAutonomy(Double.toString(row.getCell(2).getNumericCellValue()));

                ModelBrandModel model = new ModelBrandModel();
                model.setId(id);
                model.setKnown(true);
                model.setBrand_model(brand);
                model.setUser_model(user);
                model_brand_repo.save(model);

                models.add(model);
            }

        // Close the input stream and return the list of people
        input_stream.close();
        return models;
    }
}
