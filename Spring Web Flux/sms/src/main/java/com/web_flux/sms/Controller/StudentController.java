package com.web_flux.sms.Controller;

import com.web_flux.sms.Entity.Student;
import com.web_flux.sms.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Flux<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Mono<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public Mono<Student> createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @PutMapping("/{id}")
    public Mono<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        return studentService.saveStudent(student);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
