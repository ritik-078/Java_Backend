package com.spring_web_flux.tutorial.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("courses")
public class Courses {
    @Id
    @Column("id")
    private Long id;

    @Column("coursename")
    private String coursename;
}