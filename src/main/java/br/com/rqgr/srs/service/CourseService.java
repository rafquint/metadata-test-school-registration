package br.com.rqgr.srs.service;

import br.com.rqgr.srs.dto.CourseDTO;
import br.com.rqgr.srs.model.Course;
import br.com.rqgr.srs.repository.CourseRepository;
import java.util.Optional;
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
public class CourseService {

    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(ModelMapper modelMapper, CourseRepository courseRepository) {
        this.modelMapper = modelMapper;
        this.courseRepository = courseRepository;
    }

    public Page<CourseDTO> findAll(Pageable pageable) {
        final Page<Course> courses = courseRepository.findAll(pageable);
        return courses.map(CourseDTO::new);
    }

    public Optional<CourseDTO> findById(String id) {
        final Optional<Course> course = courseRepository.findById(id);
        return course.map(CourseDTO::new);
    }

    public CourseDTO create(CourseDTO courseDTO) {
        final Course course = modelMapper.map(courseDTO, Course.class);
        final Course savedCourse = courseRepository.save(course);
        return new CourseDTO(savedCourse);
    }

    public Optional<CourseDTO> update(CourseDTO courseDTO) {
        Optional<Course> courseToUpdate = courseRepository.findById(courseDTO.getId());
        courseToUpdate.ifPresent(
                (course) -> {
                    course.setName(courseDTO.getName());
                });
        return courseToUpdate.map(CourseDTO::new);
    }

    public void delete(String id) {
        courseRepository.deleteById(id);
    }

}
