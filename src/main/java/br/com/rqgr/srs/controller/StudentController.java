package br.com.rqgr.srs.controller;

import br.com.rqgr.infrastructure.ResponseUtil;
import br.com.rqgr.srs.dto.StudentDTO;
import br.com.rqgr.srs.service.StudentService;
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
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public ResponseEntity<StudentDTO> create(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudentDTO = studentService.create(studentDTO);
        return new ResponseEntity<>(createdStudentDTO, HttpStatus.CREATED);
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> retrieve(
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @Min(5) @Max(100) @RequestParam(defaultValue = "30") int size
    ) {
        final Pageable paging = PageRequest.of(page, size);
        final Page<StudentDTO> studentsPage = studentService.findAll(paging);
        return ResponseUtil.wrapOrNotFound(studentsPage);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDTO> retrieve(@NotBlank @PathVariable("id") String id) {
        final Optional<StudentDTO> studentDTO = studentService.findById(id);
        return ResponseUtil.wrapOrNotFound(studentDTO);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentDTO> update(
            @NotBlank @PathVariable("id") String id,
            @NotNull @Valid @RequestBody StudentDTO studentDTO
    ) {
        if (!id.equals(studentDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The path [id] doesn't match with [studentDTO.id]");
        }
        final Optional<StudentDTO> updatedStudentDTO = studentService.update(studentDTO);
        return ResponseUtil.wrapOrNotModified(updatedStudentDTO);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> delete(@NotBlank @PathVariable("id") String id) {
        studentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO ...
}
