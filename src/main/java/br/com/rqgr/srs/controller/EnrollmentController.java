package br.com.rqgr.srs.controller;

import br.com.rqgr.infrastructure.ResponseUtil;
import br.com.rqgr.srs.dto.CourseDTO;
import br.com.rqgr.srs.dto.EnrollmentDTO;
import br.com.rqgr.srs.dto.StudentDTO;
import br.com.rqgr.srs.service.EnrollmentService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Rafael
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enrollments")
    public ResponseEntity<EnrollmentDTO> create(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO createdEnrollmentDTO = enrollmentService.create(enrollmentDTO);
        return new ResponseEntity<>(createdEnrollmentDTO, HttpStatus.CREATED);
    }

    @GetMapping("/enrollments/courses/{courseId}")
    public ResponseEntity<List<StudentDTO>> retrieveStudents(
            @NotBlank @PathVariable("courseId") String courseId,
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @Min(5) @Max(50) @RequestParam(defaultValue = "30") int size
    ) {

        final Pageable paging = PageRequest.of(page, size);
        final Page<StudentDTO> studentsPage = enrollmentService.findAllStudentsByCourse(courseId, paging);
        return ResponseUtil.wrapOrNotFound(studentsPage);
    }
    
    @GetMapping("/enrollments/students/{studentId}")
    public ResponseEntity<List<CourseDTO>> retrieveCourses(
            @NotBlank @PathVariable("studentId") String studentId,
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @Min(5) @Max(50) @RequestParam(defaultValue = "30") int size
    ) {

        final Pageable paging = PageRequest.of(page, size);
        final Page<CourseDTO> coursesPage = enrollmentService.findAllCoursesByStudent(studentId, paging);
        return ResponseUtil.wrapOrNotFound(coursesPage);
    }
}
