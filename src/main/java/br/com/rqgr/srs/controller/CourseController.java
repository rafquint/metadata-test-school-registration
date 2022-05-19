package br.com.rqgr.srs.controller;

import br.com.rqgr.infrastructure.ResponseUtil;
import br.com.rqgr.srs.dto.CourseDTO;
import br.com.rqgr.srs.service.CourseService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Rafael
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseDTO> create(@Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO createdCourseDTO = courseService.create(courseDTO);
        return new ResponseEntity<>(createdCourseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> retrieve(
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @Min(5) @Max(100) @RequestParam(defaultValue = "30") int size
    ) {
        final Pageable paging = PageRequest.of(page, size);
        final Page<CourseDTO> coursesPage = courseService.findAll(paging);
        return ResponseUtil.wrapOrNotFound(coursesPage);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseDTO> retrieve(@NotBlank @PathVariable("id") String id) {
        final Optional<CourseDTO> courseDTO = courseService.findById(id);
        return ResponseUtil.wrapOrNotFound(courseDTO);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<CourseDTO> update(
            @NotBlank @PathVariable("id") String id,
            @NotNull @Valid @RequestBody CourseDTO courseDTO
    ) {
        if (!id.equals(courseDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The path [id] doesn't match with [courseDTO.id]");
        }
        final Optional<CourseDTO> updatedCourseDTO = courseService.update(courseDTO);
        return ResponseUtil.wrapOrNotModified(updatedCourseDTO);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<HttpStatus> delete(@NotBlank @PathVariable("id") String id) {
        courseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO ...
}
