package com.spring_web_flux.tutorial.controller;

import com.spring_web_flux.tutorial.Entity.Student;
import com.spring_web_flux.tutorial.Repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class StudentController {
    private final StudentRepository studentRepository;

    @GetMapping("/students/{studentID}")
    Mono<ResponseEntity<Student>> getStudent(@PathVariable Long studentID) {
        return studentRepository.findById(studentID).map(student -> {
            return new ResponseEntity<>(student, HttpStatus.OK);
        });
    }

    @PostMapping("/students")
    Mono<ResponseEntity<Student>> addStudent(@RequestBody Student studentAdd) {
        studentAdd.setRegisteredOn(System.currentTimeMillis());
        studentAdd.setStatus(1);
        return studentRepository.save(studentAdd).map(student -> {
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        });
    }

    @PutMapping("/students/{studentID}")
    Mono<ResponseEntity<Student>> updateStudent(@PathVariable Long studentID, @RequestBody Student newStudentData) {

        return studentRepository.findById(studentID)
                .switchIfEmpty(Mono.error(new Exception("Student with ID " + studentID + " not found")))
                .flatMap(foundStudent -> {
                    foundStudent.setName(newStudentData.getName());

                    return studentRepository.save(foundStudent).map(student -> {
                        return new ResponseEntity<>(student, HttpStatus.ACCEPTED);
                    });
                });

    }
}