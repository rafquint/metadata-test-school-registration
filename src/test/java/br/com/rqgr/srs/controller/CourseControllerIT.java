package br.com.rqgr.srs.controller;

import br.com.rqgr.srs.dto.CourseDTO;
import br.com.rqgr.srs.model.Course;
import br.com.rqgr.srs.repository.CourseRepository;
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
public class CourseControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private CourseRepository courseRepository;

    public CourseControllerIT() {
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
        final long expCount = courseRepository.count() + 1;
        final String courseName = "Translation";
        CourseDTO courseDTO = CourseDTO.builder().name(courseName).build();
        ResponseEntity<CourseDTO> result = restTemplate.postForEntity("/api/courses", courseDTO, CourseDTO.class);
        final CourseDTO body = result.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(courseName, body.getName());
        assertEquals(expCount, courseRepository.count());
    }

    @Test
    public void testRetrieve_int_int() {
        log.info("retrieve");
        final int page = 0;
        final int size = 5;
        ResponseEntity<List<CourseDTO>> result = restTemplate.exchange(
                "/api/courses?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CourseDTO>>() {
        });
        final List<CourseDTO> body = result.getBody();
        assertNotNull(body);
        assertFalse(body.isEmpty());
        assertEquals(size, body.size());
        for (CourseDTO c : body) {
            assertTrue(StringUtils.isNoneBlank(c.getId()));
            assertTrue(StringUtils.isNoneBlank(c.getName()));
        }
    }

    @Test
    public void testRetrieve_String() {
        log.info("retrieve");
        final String id = "4CB3291D2C88A88CACC354CE44931CF5";
        final String expCourseName = "History";
        ResponseEntity<CourseDTO> result = restTemplate.getForEntity("/api/courses/" + id, CourseDTO.class);
        final CourseDTO body = result.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(expCourseName, body.getName());
    }

    @Test
    public void testUpdate() {
        log.info("update");
        final String id = "40340D6E12A7D568836E642C4176C5BC";
        final Optional<Course> course = courseRepository.findById(id);
        final String courseOriginalName = course.get().getName();
        final String newCourseName = courseOriginalName + " I";
        final CourseDTO courseDTO = CourseDTO.builder().id(id).name(newCourseName).build();
        final HttpEntity<CourseDTO> requestEntity = new HttpEntity(courseDTO);
        ResponseEntity<CourseDTO> result = restTemplate.exchange("/api/courses/" + id, HttpMethod.PUT, requestEntity, CourseDTO.class);
        final CourseDTO body = result.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals(id, body.getId());
        assertEquals(newCourseName, body.getName());
        
        final Optional<Course> courseUpdated = courseRepository.findById(id);
        assertNotEquals(courseOriginalName, courseUpdated.get().getName());
    }

    @Test
    public void testDelete() {
        log.info("delete");
        final String id = "4DD3E9BACF2BB8DD837D7FDF9FED45C7";
        final long expCount = courseRepository.count() - 1;
        HttpStatus expResult = HttpStatus.OK;
        ResponseEntity<HttpStatus> result = restTemplate.exchange("/api/courses/" + id, HttpMethod.DELETE, null, HttpStatus.class);
        assertEquals(expResult, result.getStatusCode());
        assertEquals(expCount, courseRepository.count());
    }

}
