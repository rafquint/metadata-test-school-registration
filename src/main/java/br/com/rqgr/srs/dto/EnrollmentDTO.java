package br.com.rqgr.srs.dto;

import br.com.rqgr.srs.model.StudentCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {

    private String id;
    private String courseId;
    private String studentId;
    
    public EnrollmentDTO(StudentCourse studentCourse){
        this.id = studentCourse.getId();
        this.courseId = studentCourse.getCourse().getId();
        this.studentId = studentCourse.getStudent().getId();
    }

}
