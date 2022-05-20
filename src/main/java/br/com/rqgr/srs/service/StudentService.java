package br.com.rqgr.srs.service;

import br.com.rqgr.srs.dto.StudentDTO;
import br.com.rqgr.srs.model.Student;
import br.com.rqgr.srs.repository.StudentRepository;
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
public class StudentService {

    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(ModelMapper modelMapper, StudentRepository studentRepository) {
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
    }

    public Page<StudentDTO> findAll(Pageable pageable) {
        final Page<Student> students = studentRepository.findAll(pageable);
        return students.map(StudentDTO::new);
    }

    public Optional<StudentDTO> findById(String id) {
        final Optional<Student> student = studentRepository.findById(id);
        return student.map(StudentDTO::new);
    }

    public StudentDTO create(StudentDTO studentDTO) {
        final Student student = modelMapper.map(studentDTO, Student.class);
        final Student savedStudent = studentRepository.save(student);
        return new StudentDTO(savedStudent);
    }

    public Optional<StudentDTO> update(StudentDTO studentDTO) {
        Optional<Student> studentToUpdate = studentRepository.findById(studentDTO.getId());
        studentToUpdate.ifPresent(
                (student) -> {
                    student.setName(studentDTO.getName());
                });
        return studentToUpdate.map(StudentDTO::new);
    }

    public void delete(String id) {
        studentRepository.deleteById(id);
    }

}
