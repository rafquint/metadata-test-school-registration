package br.com.rqgr.srs.controller;

import br.com.rqgr.srs.dto.StudentDTO;
import br.com.rqgr.srs.model.Student;
import br.com.rqgr.srs.repository.StudentRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 *
 * @author Rafael
 */
@Slf4j
@DatabaseSetup("/data.xml")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({
    TransactionalTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
public class StudentControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
   
    @Autowired
    private StudentRepository studentRepository;

    public StudentControllerIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreate() {
        log.info("create");
        final long expCount = studentRepository.count() + 1;
        final String studentName = "Donald Duck";
        StudentDTO studentDTO = StudentDTO.builder().name(studentName).build();
        ResponseEntity<StudentDTO> result = restTemplate.postForEntity("/api/students", studentDTO, StudentDTO.class);
        final StudentDTO body = result.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(studentName, body.getName());
        assertEquals(expCount, studentRepository.count());
    }

    @Test
    public void testRetrieve_int_int() {
        log.info("retrieve");
        final int page = 0;
        final int size = 5;
        ResponseEntity<List<StudentDTO>> result = restTemplate.exchange(
                "/api/students?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDTO>>() {
        });
        final List<StudentDTO> body = result.getBody();
        assertNotNull(body);
        assertFalse(body.isEmpty());
        assertEquals(size, body.size());
        for (StudentDTO c : body) {
            assertTrue(StringUtils.isNoneBlank(c.getId()));
            assertTrue(StringUtils.isNoneBlank(c.getName()));
        }
    }

    @Test
    public void testRetrieve_String() {
        log.info("retrieve");
        final String id = "487F0DF2554795AC861262BDA03DCD8C";
        final String expStudentName = "Fay Daway";
        ResponseEntity<StudentDTO> result = restTemplate.getForEntity("/api/students/" + id, StudentDTO.class);
        final StudentDTO body = result.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(expStudentName, body.getName());
    }
    
    @Test
    public void testRetrieve_String_NotFound() {
        log.info("retrieve");
        final String id = "11D8EEBC58E0A7D796690800200C9A66";
        ResponseEntity<String> result = restTemplate.getForEntity("/api/students/" + id, String.class);
        final HttpStatus statusCode = result.getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, statusCode);
    }

    @Test
    public void testUpdate() {
        log.info("update");
        final String id = "487F0DF2554795AC861262BDA03DCD8C";
        final Optional<Student> student = studentRepository.findById(id);
        final String studentOriginalName = student.get().getName();
        final String newStudentName = studentOriginalName + " Down";
        final StudentDTO studentDTO = StudentDTO.builder().id(id).name(newStudentName).build();
        final HttpEntity<StudentDTO> requestEntity = new HttpEntity(studentDTO);
        ResponseEntity<StudentDTO> result = restTemplate.exchange("/api/students/" + id, HttpMethod.PUT, requestEntity, StudentDTO.class);
        final StudentDTO body = result.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(id, body.getId());
        assertEquals(newStudentName, body.getName());
       
        final Optional<Student> studentUpdated = studentRepository.findById(id);
        assertNotEquals(studentOriginalName, studentUpdated.get().getName());
    }

    @Test
    public void testDelete() {
        log.info("delete");
        final String id = "44494CB94BCFC8199810F34553BED502";
        final long expCount = studentRepository.count() - 1;
        HttpStatus expResult = HttpStatus.OK;
        ResponseEntity<HttpStatus> result = restTemplate.exchange("/api/students/" + id, HttpMethod.DELETE, null, HttpStatus.class);
        assertEquals(expResult, result.getStatusCode());
        assertEquals(expCount, studentRepository.count());
    }

}
