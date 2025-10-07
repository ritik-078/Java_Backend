package com.web_flux.sms.Service;

import com.web_flux.sms.Entity.Student;
import com.web_flux.sms.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Flux<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Mono<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Mono<Student> saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Mono<Void> deleteStudent(Long id) {
        return studentRepository.deleteById(id);
    }
}
