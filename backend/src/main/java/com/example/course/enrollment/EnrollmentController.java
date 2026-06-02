package com.example.course.enrollment;

import com.example.course.course.CourseService;
import com.example.course.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping("/enrollments")
    public String enrollments(@RequestParam(required = false) Long studentId, Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("enrollments", enrollmentService.findByStudentId(studentId));
        model.addAttribute("selectedStudentId", studentId);
        return "enrollments";
    }

    @PostMapping("/enrollments")
    public String enroll(Long studentId, Long courseId, Model model) {
        try {
            enrollmentService.enroll(studentId, courseId);
            return "redirect:/enrollments?studentId=" + studentId;
        } catch (Exception e) {
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("enrollments", enrollmentService.findByStudentId(studentId));
            model.addAttribute("selectedStudentId", studentId);
            model.addAttribute("errorMessage", e.getMessage());
            return "enrollments";
        }
    }

    @PostMapping("/enrollments/cancel")
    public String cancel(Long studentId, Long courseId, Model model) {
        try {
            enrollmentService.cancel(studentId, courseId);
            return "redirect:/enrollments?studentId=" + studentId;
        } catch (Exception e) {
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("enrollments", enrollmentService.findByStudentId(studentId));
            model.addAttribute("selectedStudentId", studentId);
            model.addAttribute("errorMessage", e.getMessage());
            return "enrollments";
        }
    }
}