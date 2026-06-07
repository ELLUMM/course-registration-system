\set sid random(1,1000)
\set cid random(1,100)

BEGIN;

INSERT INTO enrollment(
student_id,
course_id,
enrolled_at
)
VALUES(
:sid,
:cid,
NOW()
);

UPDATE course
SET enrolled_count=enrolled_count+1
WHERE id=:cid
AND enrolled_count < capacity;

COMMIT;