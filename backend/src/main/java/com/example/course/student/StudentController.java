package com.example.course.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/students")
    public String students(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students";
    }

    @PostMapping("/students")
    public String addStudent(String studentNumber, String name, String department) {
        studentService.save(studentNumber, name, department);
        return "redirect:/students";
    }
}