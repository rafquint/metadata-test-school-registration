package br.com.rqgr.srs.service;

import br.com.rqgr.srs.dto.CourseDTO;
import br.com.rqgr.srs.dto.EnrollmentDTO;
import br.com.rqgr.srs.dto.StudentDTO;
import br.com.rqgr.srs.model.Course;
import br.com.rqgr.srs.model.Student;
import br.com.rqgr.srs.model.StudentCourse;
import br.com.rqgr.srs.repository.StudentCourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rafael
 */
@Slf4j
@Service
@Transactional
public class EnrollmentService {

    private final ModelMapper modelMapper;
    private final StudentCourseRepository studentCourseRepository;

    @Autowired
    public EnrollmentService(ModelMapper modelMapper, StudentCourseRepository studentCourseRepository) {
        this.modelMapper = modelMapper;
        this.studentCourseRepository = studentCourseRepository;
    }

    public EnrollmentDTO create(EnrollmentDTO enrollmentDTO) {
        final StudentCourse studentCourse = modelMapper.map(enrollmentDTO, StudentCourse.class);
        //TODO - verify max 50
        final StudentCourse savedStudentCourse = studentCourseRepository.save(studentCourse);
        return new EnrollmentDTO(savedStudentCourse);
    }

    public Page<StudentDTO> findAllStudentsByCourse(String courseId, Pageable pageable) {
        final Page<Student> students = studentCourseRepository.findAllAllStudentsByCourse(courseId, pageable);
        return students.map(StudentDTO::new);
    }
    
    public Page<CourseDTO> findAllCoursesByStudent(String studentId, Pageable pageable) {
        final Page<Course> courses = studentCourseRepository.findAllCoursesByStudent(studentId, pageable);
        return courses.map(CourseDTO::new);
    }

}
