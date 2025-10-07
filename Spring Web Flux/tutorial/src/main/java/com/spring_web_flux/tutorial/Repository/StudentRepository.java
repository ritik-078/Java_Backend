package com.spring_web_flux.tutorial.Repository;



import com.spring_web_flux.tutorial.Entity.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {
}