package br.com.rqgr.srs.repository;

import br.com.rqgr.srs.model.Course;
import br.com.rqgr.srs.model.Student;
import br.com.rqgr.srs.model.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Rafael
 */
public interface StudentCourseRepository extends JpaRepository<StudentCourse, String> {

    @Query(" SELECT DISTINCT sc.student"
            + " FROM StudentCourse sc"
            + " LEFT JOIN sc.course c"
            + " WHERE c.id = :courseId")
    public Page<Student> findAllAllStudentsByCourse(String courseId, Pageable pageable);

    @Query(" SELECT DISTINCT sc.course"
            + " FROM StudentCourse sc"
            + " LEFT JOIN sc.student s"
            + " WHERE s.id = :studentId")
    public Page<Course> findAllCoursesByStudent(String studentId, Pageable pageable);

}
