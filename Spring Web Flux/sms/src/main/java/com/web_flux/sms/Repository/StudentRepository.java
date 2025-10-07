package com.web_flux.sms.Repository;

import com.web_flux.sms.Entity.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {
}
