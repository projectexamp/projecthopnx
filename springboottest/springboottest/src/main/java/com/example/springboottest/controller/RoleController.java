package com.example.springboottest.controller;

import com.example.springboottest.model.entity.Function;
import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.RoleFunction;
import com.example.springboottest.model.entity.RoleUser;
import com.example.springboottest.model.repository.FunctionRepository;
import com.example.springboottest.model.repository.RoleFunctionRepository;
import com.example.springboottest.model.repository.RoleRepository;
import com.example.springboottest.model.service.RoleService;
import com.example.springboottest.model.service.serviceImp.RoleFunctionServiceImpl;
import com.example.springboottest.model.service.serviceImp.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleFunctionRepository roleFunctionRepository ;

    @Autowired
    private RoleFunctionServiceImpl roleFunctionService ;

    @Autowired
    private FunctionRepository functionRepository ;


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

    @PostMapping("/getRoleActiveByUser")
    public ResponseEntity<List<Role>> getFunctionActiveByRole(@RequestBody RoleUser search ) {
        List roles = roleRepository.getListRoleActiveByUser(search.getUserId());
        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);

    }


    @GetMapping("/getRoleListAll")
    public ResponseEntity<List<Role>> getFunctionListAll() {
        List roles = roleRepository.getListRoleAll();
        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);

    }


    @Transactional
    @PostMapping("/saveRole")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        String[] listFuncInputStr  = role.getFunc() ;
        List<Long> listFuncInput = new ArrayList<>();
//        HashMap<Long,Long> MapFuncIdInput = new HashMap<>() ;
//        HashMap<Long,Long> MapFuncIdDb = new HashMap<>() ;
//        List<RoleFunction> listRoleFuncDb = roleFunctionRepository.findRoleFunctionByRoleId(role.getId()) ;
//
//        List<Long> listFuncIdInActive = new ArrayList<>();
//        List<Long> listFuncIdActive  = new ArrayList<>() ;
//        List<RoleFunction> listRoleFunctionActive =new ArrayList<>();

        //convert to ArrayList
        for (int i =0 ; i < listFuncInputStr.length ; i++ ){
            listFuncInput.add(Long.parseLong(listFuncInputStr[i])) ;
        }

//        //convert to hashmap
//        for (int i =0 ; i < listFuncInputStr.length ; i++ ){
//            MapFuncIdInput.put(Long.parseLong(listFuncInputStr[i]),Long.parseLong(listFuncInputStr[i])) ;
//        }
//        for ( RoleFunction rf  : listRoleFuncDb ){
//            MapFuncIdDb.put(rf.getFunctionId(),rf.getFunctionId()) ;
//        }
//
//        //add list InActive
//        for (RoleFunction rf : listRoleFuncDb) {
//            if (MapFuncIdInput.get(rf.getFunctionId()) != null ){
//                continue;
//            }else {
//                listFuncIdInActive.add(rf.getFunctionId()) ;
//            }
//        }
//
//        //add list Active
//        for(Long i : listFuncInput ){
//            if (MapFuncIdDb.get(i) != null ){
//                continue;
//            }else {
//                listFuncIdActive.add(MapFuncIdInput.get(i)) ;
//            }
//        }
//
//        for (Long l  : listFuncIdInActive){
//            System.out.println("id inActive : " + l);
//        }
//        System.out.println("danh sach inActive " +  listFuncIdInActive);
//        System.out.println("danh sach active " +  listFuncIdActive);
//
//        if(listFuncIdActive.size()!=0 ){
//            RoleFunction roleFunction = null ;
//            for(Long i : listFuncIdActive ){
//                roleFunction = new RoleFunction() ;
//                roleFunction.setFunctionId(i);
//                roleFunction.setIsActive((long) 0);
//                roleFunction.setRoleId(role.getId());
//                roleFunctionService.save(roleFunction) ;
//            }
//
//        }
//
//        if(listFuncIdInActive.size() > 0 ){
//            roleFunctionRepository.InActiveRoleFunction(role.getId(),listFuncIdInActive);
//        }
//        MapFuncIdInput.get
//        System.out.println(listRoleFunc.contains(1));


        role.setFunctions(functionRepository.finByFuncId(listFuncInput));
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
    

