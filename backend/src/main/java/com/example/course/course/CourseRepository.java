package com.example.course.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCourseNameContainingIgnoreCaseOrProfessorContainingIgnoreCase(
            String courseName,
            String professor
    );

    @Modifying
    @Query("""
        update Course c
        set c.enrolledCount = c.enrolledCount + 1
        where c.id = :courseId
          and c.enrolledCount < c.capacity
    """)
    int increaseEnrolledCountIfAvailable(@Param("courseId") Long courseId);

    @Modifying
    @Query("""
        update Course c
        set c.enrolledCount = c.enrolledCount - 1
        where c.id = :courseId
          and c.enrolledCount > 0
    """)
    int decreaseEnrolledCount(@Param("courseId") Long courseId);
}