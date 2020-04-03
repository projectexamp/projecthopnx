package com.example.springboottest.controller;

import com.example.springboottest.model.entity.Function;
import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.repository.RoleRepository;
import com.example.springboottest.model.service.serviceImp.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private RoleRepository roleRepository;



    @PostMapping("/getRoleList")
    public ResponseEntity<List<Role>> getRoleList(@RequestBody Role search) {
        List<Role> roles = roleRepository.getListRole(search.getRoleName(), search.getRoleCode());
        for(Role r  : roles){
            String funcStr = "";
            for(Function f : r.getFunctions()){
                funcStr += f.getId() +",";
            }
            r.setFuncStr(funcStr);
//            Set<Function> functions = r.getFunctions() ;
//            for(Function f  : r.getFunctions()){
//                funcStr +=
//            }
        }
        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);

    }
    
    @PostMapping("/saveRole")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        Role r = roleService.save(role);
        return new ResponseEntity<Role>(r, HttpStatus.OK);
    }
    
    @PostMapping("/deleteRole")
    public ResponseEntity<Boolean> deleteRole(@RequestBody Role role) {
        if(role.getId()>0){
            roleService.delete(role);
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
//                roleService.save(student);
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
//                roleRepository.saveAll(students) ;
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
    

