package com.example.course.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public void save(String studentNumber, String name, String department) {
        Student student = new Student(studentNumber, name, department);
        studentRepository.save(student);
    }
}