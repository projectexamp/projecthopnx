package com.example.springboottest.controller;

import com.example.springboottest.model.entity.Function;
import com.example.springboottest.model.entity.User;
import com.example.springboottest.model.repository.FunctionRepository;
import com.example.springboottest.model.service.serviceImp.FunctionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FunctionController {

    @Autowired
    private FunctionServiceImpl functionService;
    @Autowired
    private FunctionRepository functionRepository;



    @PostMapping("/getFunctionList")
    public ResponseEntity<List<Function>> getFunctionList(@RequestBody Function search) {
        List functions = functionRepository.getListFunction(search.getFunctionName(), search.getFunctionCode());
        return new ResponseEntity<List<Function>>(functions, HttpStatus.OK);

    }

    @GetMapping("/getFunctionListAll")
    public ResponseEntity<List<Function>> getFunctionListAll() {
        List functions = functionRepository.getListFunctionAll();
        return new ResponseEntity<List<Function>>(functions, HttpStatus.OK);

    }
    
    @PostMapping("/saveFunction")
    public ResponseEntity<Function> saveUser(@RequestBody Function function) {
        Function st = functionService.save(function);
        return new ResponseEntity<Function>(st, HttpStatus.OK);
    }
    
    @PostMapping("/deleteFunction")
    public ResponseEntity<Boolean> deleteUser(@RequestBody Function function) {
        if(function.getId()>0){
            functionService.delete(function);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
         
        return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
    }
    
//    @Secured("ROLE_ADMIN")
//    @PostMapping("/uploadStudent")
//    public String uploadStudent(@RequestParam("file") MultipartFile file) throws ParseException {
//        try {
//            Workbook workbook = new XSSFWorkbook(file.getInputStream());
//            Sheet datatypeSheet = workbook.getSheetAt(0);
//
//            Iterator<Row> iterator = datatypeSheet.iterator();
//            iterator.next();
//
//            List<Student> studentList = new ArrayList<Student>();
//            while (iterator.hasNext()) {
//                Row currentRow = iterator.next();
//                if (currentRow.getCell(0) == null || String.valueOf(currentRow.getCell(0))=="") {
//                    break;
//                }
//                Student student = new Student(
//                        String.valueOf(currentRow.getCell(0)),
//                        String.valueOf(currentRow.getCell(1)),
//                        String.valueOf(currentRow.getCell(2)),
//                        String.valueOf(currentRow.getCell(3))
//                       );
//
//                studentList.add(student);
//                functionService.save(student);
//            }
//            workbook.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Done");
//
//        return "Sucess";
//    }

//    @PostMapping("/upload-csv-file")
//    public String uploadCSVFile(@RequestParam("file") MultipartFile file) {
//
//
//        List<Student> students = new ArrayList<Student>();
//            // parse CSV file to create a list of `User` objects
//            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//
//                // create csv bean reader
//                CsvToBean<StudentCsvModel> csvToBean = new CsvToBeanBuilder(reader)
//                        .withType(StudentCsvModel.class)
//                        .withIgnoreLeadingWhiteSpace(true)
//                        .build();
//
//                // convert `CsvToBean` object to list of users
//                List<StudentCsvModel> studentCsvModels = csvToBean.parse();
//
//                // TODO: save users in DB?
//                for (StudentCsvModel sm : studentCsvModels ){
//                    Student student = new Student(sm.getName(), sm.getPhoneNumber(), sm.getAddr(), sm.getBirthDate()) ;
//                    students.add(student) ;
//                }
//                functionRepository.saveAll(students) ;
//                // save users list on model
//
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
////        }
//
//        return "file-upload-status";
//    }






}
    

