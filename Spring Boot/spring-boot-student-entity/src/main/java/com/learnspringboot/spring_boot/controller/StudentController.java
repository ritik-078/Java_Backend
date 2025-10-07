package com.learnspringboot.spring_boot.controller;

import com.learnspringboot.spring_boot.bean.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("student")
public class StudentController {


    // Using Response Entity(it is used to send response to http requests)
    @GetMapping("")
    public ResponseEntity<Student> getStudent(){
        Student student = new Student("Ritik ", 7, "Bhateley");
//        return new ResponseEntity<>(student, HttpStatus.OK);
//        return ResponseEntity.ok(student);
        return ResponseEntity.ok()
                .header("custom-header","custom-header-value")
                .body(student);
    }



    // Returns List as Json-object
    @GetMapping("all")
    public ResponseEntity<List<Student>> getStudentList(){
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Ritik", 7, "Bhateley"));
        studentList.add(new Student("Parth", 19, "Bhateley"));
        studentList.add(new Student("Rohit", 4, "Tiwari"));
        studentList.add(new Student("Ram", 12, "Kumar"));
        studentList.add(new Student("Shyam", 29, "Sharma"));
        return ResponseEntity.ok(studentList);
    }



    // REST API with Path Variable
    // https://localhost:8080/student/2
    @GetMapping("{id}")
    public  ResponseEntity<Student> studentPathID(@PathVariable("id") int studentId){
        Student student = new Student("Ritik", studentId, "Bhateley");
        return ResponseEntity.ok(student);
    }

    // Multiple Path Variables
    @GetMapping("{id}/{fName}/{lName}")
    public  ResponseEntity<Student> studentPathIDs(@PathVariable("id") int studentId,
                                   @PathVariable("fName") String firstName,
                                   @PathVariable("lName") String lastName){
        Student student = new Student(firstName, studentId, lastName);
        return ResponseEntity.ok(student);
    }



    // REST API with Request Params
    // http://localhost:8080/student/query?id=1
//    @GetMapping("student/query")
//    public Student studentRequestVariable(@RequestParam int id){
//        return new Student("Ritik",id,"Bhateley");
//    }

    // http://localhost:8080/student/query?id=1&firstName=Ritik&lastName=Bhateley
    @GetMapping("query")
    public ResponseEntity<Student> studentRequestVariables(@RequestParam int id,
                                           @RequestParam String firstName,
                                           @RequestParam String lastName){
        Student student = new Student(firstName, id, lastName);
        return ResponseEntity.ok(student);
    }




    // POST


    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        System.out.println(student.getId() + " " + student.getFirstName() + " " + student.getLastName());
        return ResponseEntity.ok(student);
    }



    // PUT

    @PutMapping("{id}/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable("id") int studentID){
        student.setId(studentID);
        System.out.println(student.getId() + " " + student.getFirstName() + " " + student.getLastName());
        return ResponseEntity.ok(student);
    }



    // DELETE

    @DeleteMapping("{id}/delete")
    public String deleteStudent(@PathVariable("id") int studentID){
        System.out.println(studentID);
        return "Deleted Successfully";
    }




//    // GET
//
//    // Returns Java-bean as Json-object
////    @GetMapping("student")
////    public Student getStudent(){
////        return new Student("Ritik ", 7, "Bhateley");
////    }
//
//
//    // Returns List as Json-object
//    @GetMapping("students")
//    public List<Student> getStudentList(){
//        List<Student> studentList = new ArrayList<>();
//        studentList.add(new Student("Ritik", 7, "Bhateley"));
//        studentList.add(new Student("Parth", 19, "Bhateley"));
//        studentList.add(new Student("Rohit", 4, "Tiwari"));
//        studentList.add(new Student("Ram", 12, "Kumar"));
//        studentList.add(new Student("Shyam", 29, "Sharma"));
//        return studentList;
//    }
//
//
//
//    // REST API with Path Variable
//    // https://localhost:8080/student/2
//    @GetMapping("student/{id}")
//    public  Student studentPathID(@PathVariable("id") int studentId){
//        return new Student("Ritik", studentId, "Bhateley");
//    }
//
//    // Multiple Path Variables
//    @GetMapping("student/{id}/{fName}/{lName}")
//    public  Student studentPathIDs(@PathVariable("id") int studentId,
//                                   @PathVariable("fName") String firstName,
//                                   @PathVariable("lName") String lastName){
//        return new Student(firstName, studentId, lastName);
//    }
//
//
//
//    // REST API with Request Params
//    // http://localhost:8080/student/query?id=1
////    @GetMapping("student/query")
////    public Student studentRequestVariable(@RequestParam int id){
////        return new Student("Ritik",id,"Bhateley");
////    }
//
//    // http://localhost:8080/student/query?id=1&firstName=Ritik&lastName=Bhateley
//    @GetMapping("student/query")
//    public Student studentRequestVariables(@RequestParam int id,
//                                           @RequestParam String firstName,
//                                           @RequestParam String lastName){
//        return new Student(firstName,id,lastName);
//    }
//
//
//
//
//    // POST
//
//
//    @PostMapping("student/create")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Student createStudent(@RequestBody Student student){
//        System.out.println(student.getId() + " " + student.getFirstName() + " " + student.getLastName());
//        return student;
//    }
//
//
//
//    // PUT
//
//    @PutMapping("student/{id}/update")
//    public Student updateStudent(@RequestBody Student student, @PathVariable("id") int studentID){
//        student.setId(studentID);
//        System.out.println(student.getId() + " " + student.getFirstName() + " " + student.getLastName());
//        return student;
//    }
//
//
//
//    // DELETE
//
//    @DeleteMapping("student/{id}/delete")
//    public String deleteStudent(@PathVariable("id") int studentID){
//        System.out.println(studentID);
//        return "Deleted Successfully";
//    }
//
//




}
