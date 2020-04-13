package com.example.springboottest.controller;

import com.example.springboottest.model.entity.RoleFunction;
import com.example.springboottest.model.entity.RoleUser;
import com.example.springboottest.model.entity.Student;
import com.example.springboottest.model.entity.User;
import com.example.springboottest.model.repository.RoleRepository;
import com.example.springboottest.model.repository.RoleUserRepository;
import com.example.springboottest.model.repository.UserRepository;
import com.example.springboottest.model.service.RoleUserService;
import com.example.springboottest.model.service.serviceImp.UserServiceImpl;
import com.example.springboottest.viewmodel.StudentCsvModel;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleUserRepository roleUserRepository ;
    @Autowired
    private RoleUserService roleUserService ;

    @Autowired
    private RoleRepository roleRepository ;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder ;

    @PostMapping("/getUserList")
    public ResponseEntity<List<User>> getUserList(@RequestBody User search) {
        List users = userRepository.getListUser(search.getUsername(), search.getFullname());
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }

    @Transactional
    @PostMapping("/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        String[] listRoleInputStr  = user.getRole() ;
        List<Long> listRoleInput = new ArrayList<>();
        User userClone =  userRepository.findById(user.getId()).get();
//        HashMap<Long,Long> MapRoleIdInput = new HashMap<>() ;
//        HashMap<Long,Long> MapRoleIdDb = new HashMap<>() ;
//        List<RoleUser> listRoleUserDb = roleUserRepository.findRoleUserActiveByUserId(user.getId()) ;
//
//        List<Long> listRoleIdInActive = new ArrayList<>();
//        List<Long> listRoleIdActive  = new ArrayList<>() ;
//        List<RoleUser> listRoleUserActive =new ArrayList<>();

        //convert to ArrayList
        for (int i =0 ; i < listRoleInputStr.length ; i++ ){
            listRoleInput.add(Long.parseLong(listRoleInputStr[i])) ;
        }

        //convert to hashmap
//        for (int i =0 ; i < listRoleInputStr.length ; i++ ){
//            MapRoleIdInput.put(Long.parseLong(listRoleInputStr[i]),Long.parseLong(listRoleInputStr[i])) ;
//        }
//        for ( RoleUser ru  : listRoleUserDb ){
//            MapRoleIdDb.put(ru.getRoleId(),ru.getRoleId()) ;
//        }
//
//        //add list InActive
//        for (RoleUser ru : listRoleUserDb) {
//            if (MapRoleIdInput.get(ru.getRoleId()) != null ){
//                continue;
//            }else {
//                listRoleIdInActive.add(ru.getRoleId()) ;
//            }
//        }
//
//        //add list Active
//        for(Long i : listRoleInput ){
//            if (MapRoleIdDb.get(i) != null ){
//                continue;
//            }else {
//                listRoleIdActive.add(MapRoleIdInput.get(i)) ;
//            }
//        }
//
//        for (Long l  : listRoleIdInActive){
//            System.out.println("id inActive : " + l);
//        }
//        System.out.println("danh sach inActive " +  listRoleIdInActive);
//        System.out.println("danh sach active " +  listRoleIdActive);
        String pass = user.getPassword().trim() ;
        String passEncode = null ;
        if(pass != null && !pass.equals("") ){
            passEncode  =  bCryptPasswordEncoder.encode(pass) ;
        }else{
            passEncode = userClone.getPassword() ;
        }
        user.setPassword(passEncode);
        user.setRoles(roleRepository.finByRoleId(listRoleInput));
        User st = userService.save(user);
        return new ResponseEntity<User>(st, HttpStatus.OK);
    }
    
    @PostMapping("/deleteUser")
    public ResponseEntity<Boolean> deleteUser(@RequestBody User user) {
        if(user.getId()>0){
            userService.delete(user);
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
//                userService.save(student);
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
//                userRepository.saveAll(students) ;
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
    

