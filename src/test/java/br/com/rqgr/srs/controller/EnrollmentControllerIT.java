package br.com.rqgr.srs.controller;

import br.com.rqgr.srs.dto.CourseDTO;
import br.com.rqgr.srs.dto.EnrollmentDTO;
import br.com.rqgr.srs.dto.StudentDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
public class EnrollmentControllerIT {
    
    private static final StudentDTO STUDENT_1 = StudentDTO.builder().id("4EEFE4883F938B38BAC6E2EC7D5BBC56").name("STUDENT I").build();
    private static final StudentDTO STUDENT_2 = StudentDTO.builder().id("4CC290A5D9090A089B3D508543AE82BD").name("STUDENT II").build();
    private static final CourseDTO COURSE_1 = CourseDTO.builder().id("47305932B29CC195B355CAB3E5BF9D9C").name("COURSE I").build();
    private static final CourseDTO COURSE_2 = CourseDTO.builder().id("4D3150A45B251017B2B3D6BA149B0D30").name("COURSE II").build();
    private static final CourseDTO COURSE_3 = CourseDTO.builder().id("4753CD1A36709BD780021778585A023D").name("COURSE IIII").build();
    private static final String BIOLOGY_ID = "42CF9FB8E2A4B2BDBD9CD6208A0E797C";
    private static final String PHILOSOPHY_ID = "4C741A95BFB5A10EA2FCBA128DC8763B";
    private static final String DEE_END_ID = "4E437ADCD01D42E5B67294503DD080F2";
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    public EnrollmentControllerIT() {
    }

    @Test
    public void testCreate() {
        log.info("create");
        EnrollmentDTO enrollmentDTO = EnrollmentDTO.builder().courseId(COURSE_1.getId()).studentId(STUDENT_1.getId()).build();
        ResponseEntity<EnrollmentDTO> result = restTemplate.postForEntity("/api/enrollments", enrollmentDTO, EnrollmentDTO.class);
        final EnrollmentDTO body1 = result.getBody();
        assertNotNull(body1);
        assertTrue(StringUtils.isNotBlank(body1.getId()));
        assertEquals(COURSE_1.getId(), body1.getCourseId());
        assertEquals(STUDENT_1.getId(), body1.getStudentId());
    }

    @Test
    public void testRetrieveStudents() {
        log.info("retrieveStudents");
        int page = 0;
        int size = 5;
        ResponseEntity<List<StudentDTO>> result = restTemplate.exchange(
                "/api/enrollments/courses/" + BIOLOGY_ID + "?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDTO>>() {
        });
        final List<StudentDTO> body = result.getBody();
        assertNotNull(body);
        assertFalse(body.isEmpty());
        assertEquals(size, body.size());
        for (StudentDTO s : body) {
            assertTrue(StringUtils.isNoneBlank(s.getId()));
            assertTrue(StringUtils.isNoneBlank(s.getName()));
        }
    }

    @Test
    public void testRetrieveCourses() {
        log.info("retrieveCourses");
        int page = 0;
        int size = 5;
        ResponseEntity<List<CourseDTO>> result = restTemplate.exchange(
                "/api/enrollments/students/" + DEE_END_ID + "?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CourseDTO>>() {
        });
        final List<CourseDTO> body = result.getBody();
        assertNotNull(body);
        assertFalse(body.isEmpty());
        assertTrue(size >=  body.size());
        for (CourseDTO s : body) {
            assertTrue(StringUtils.isNoneBlank(s.getId()));
            assertTrue(BIOLOGY_ID.equals(s.getId()) || PHILOSOPHY_ID.equals(s.getId()));
            assertTrue(StringUtils.isNoneBlank(s.getName()));
        }
    }

    @Test
    public void testRetrieveUnregisteredStudents() {
        log.info("retrieveUnregisteredStudents");
        int page = 0;
        int size = 50;
        ResponseEntity<List<StudentDTO>> result = restTemplate.exchange(
                "/api/enrollments/unregistered/students?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDTO>>() {
        });
        final List<StudentDTO> body = result.getBody();
        assertNotNull(body);
        assertFalse(body.isEmpty());
        assertTrue(!body.isEmpty() && size >=  body.size());
        final List<String> collected = body.stream().map(StudentDTO::getId).filter(id -> STUDENT_1.getId().equals(id) || STUDENT_2.getId().equals(id)).collect(Collectors.toList());
        assertTrue(collected.size() == 2);
        for (StudentDTO s : body) {
            assertTrue(StringUtils.isNoneBlank(s.getId()));
            assertTrue(StringUtils.isNoneBlank(s.getName()));
        }
    }

    @Test
    public void testRetrieveUnregisteredCourses() {
        log.info("retrieveUnregisteredCourses");
        int page = 0;
        int size = 50;
        ResponseEntity<List<CourseDTO>> result = restTemplate.exchange(
                "/api/enrollments/unregistered/courses?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CourseDTO>>() {
        });
        final List<CourseDTO> body = result.getBody();
        assertNotNull(body);
        assertFalse(body.isEmpty());
        assertTrue(!body.isEmpty() && size >=  body.size());
        final List<String> collected = body.stream().map(CourseDTO::getId).filter(id -> COURSE_2.getId().equals(id) || COURSE_3.getId().equals(id)).collect(Collectors.toList());
        assertTrue(collected.size() == 2);
        for (CourseDTO c : body) {
            assertTrue(StringUtils.isNoneBlank(c.getId()));
            assertTrue(StringUtils.isNoneBlank(c.getName()));
        }
    }
    
}
