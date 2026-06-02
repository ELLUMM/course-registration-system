package com.example.course.student;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

    private LocalDateTime createdAt;

    public Student(String studentNumber, String name, String department) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.department = department;
        this.createdAt = LocalDateTime.now();
    }
}