\set student_id random(1,1000)

SELECT
e.id,
s.name,
c.course_name
FROM enrollment e
JOIN student s ON e.student_id = s.id
JOIN course c ON e.course_id = c.id
WHERE s.id = :student_id;