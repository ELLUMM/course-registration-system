package com.example.course.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public String courses(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("courses", courseService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "courses";
    }

    @PostMapping("/courses")
    public String addCourse(String courseName, String professor, int capacity,
                            String dayOfWeek, String startTime, String endTime) {
        courseService.save(courseName, professor, capacity, dayOfWeek, startTime, endTime);
        return "redirect:/courses";
    }
}