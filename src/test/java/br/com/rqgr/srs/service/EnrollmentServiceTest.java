package br.com.rqgr.srs.service;

import br.com.rqgr.srs.repository.StudentCourseRepository;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Rafael
 */
@RunWith(MockitoJUnitRunner.class)
public class EnrollmentServiceTest {

    private static final String ID = "4E437ADCD01D42E5B67294503DD080F2";

    @Mock
    private StudentCourseRepository studentCourseRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    public EnrollmentServiceTest() {
    }

    @Test
    public void testVerifyMaxStudents() {
        System.out.println("verifyMaxStudents");

        when(studentCourseRepository.countByCourseId(ID)).thenReturn(50L);
        try {
            enrollmentService.verifyMaxStudents(ID);
            fail("Expected to throws ResponseStatusException");
        } catch (ResponseStatusException rse) {
            verify(studentCourseRepository, times(1)).countByCourseId(ID);
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, rse.getStatus());
        }
    }

    @Test
    public void testVerifyMaxCourses() {
        System.out.println("verifyMaxCourses");

        when(studentCourseRepository.countByStudentId(ID)).thenReturn(5L);
        try {
            enrollmentService.verifyMaxCourses(ID);
            fail("Expected to throws ResponseStatusException");
        } catch (ResponseStatusException rse) {
            verify(studentCourseRepository, times(1)).countByStudentId(ID);
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, rse.getStatus());
        }
    }
}
