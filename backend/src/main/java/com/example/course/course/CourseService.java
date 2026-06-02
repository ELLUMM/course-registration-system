package com.example.course.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public List<Course> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return courseRepository.findAll();
        }

        return courseRepository
                .findByCourseNameContainingIgnoreCaseOrProfessorContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }

    public void save(String courseName, String professor, int capacity,
                     String dayOfWeek, String startTime, String endTime) {
        Course course = new Course(
                courseName,
                professor,
                capacity,
                dayOfWeek,
                LocalTime.parse(startTime),
                LocalTime.parse(endTime)
        );

        courseRepository.save(course);
    }
}