package br.com.rqgr.srs.repository;

import br.com.rqgr.srs.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Rafael
 */
public interface StudentCourseRepository extends JpaRepository<Student, String> {
    
}
