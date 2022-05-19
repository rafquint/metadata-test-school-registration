package br.com.rqgr.srs.repository;

import br.com.rqgr.srs.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Rafael
 */
public interface CourseRepository extends JpaRepository<Course, String> {
    
}
