package com.example.course.enrollment;

import com.example.course.course.Course;
import com.example.course.course.CourseRepository;
import com.example.course.student.Student;
import com.example.course.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> findByStudentId(Long studentId) {
        if (studentId == null) {
            return enrollmentRepository.findAll();
        }

        return enrollmentRepository.findByStudent_Id(studentId);
    }

    @Transactional
    public void enroll(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생이 존재하지 않습니다."));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));

        // 1. 중복 신청 방지
        if (enrollmentRepository.existsByStudent_IdAndCourse_Id(studentId, courseId)) {
            throw new IllegalStateException("이미 신청한 강의입니다.");
        }

        // 2. 시간표 충돌 방지
        List<Enrollment> currentEnrollments = enrollmentRepository.findByStudent_Id(studentId);

        for (Enrollment enrollment : currentEnrollments) {
            Course registeredCourse = enrollment.getCourse();

            boolean sameDay = registeredCourse.getDayOfWeek().equals(course.getDayOfWeek());

            boolean timeOverlaps = isTimeOverlapping(
                    registeredCourse.getStartTime(),
                    registeredCourse.getEndTime(),
                    course.getStartTime(),
                    course.getEndTime()
            );

            if (sameDay && timeOverlaps) {
                throw new IllegalStateException("이미 신청한 강의와 시간이 겹칩니다.");
            }
        }

        // 3. 정원 초과 방지
        int updatedCount = courseRepository.increaseEnrolledCountIfAvailable(courseId);

        if (updatedCount == 0) {
            throw new IllegalStateException("정원이 마감되었습니다.");
        }

        // 4. 수강신청 내역 저장
        Enrollment enrollment = new Enrollment(student, course);
        enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void cancel(Long studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudent_IdAndCourse_Id(studentId, courseId)
                .orElseThrow(() -> new IllegalStateException("수강신청 내역이 없습니다."));

        enrollmentRepository.delete(enrollment);

        courseRepository.decreaseEnrolledCount(courseId);
    }

    private boolean isTimeOverlapping(LocalTime start1, LocalTime end1,
                                      LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
}