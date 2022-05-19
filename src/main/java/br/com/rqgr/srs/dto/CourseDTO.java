package br.com.rqgr.srs.dto;

import br.com.rqgr.srs.model.Course;
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
public class CourseDTO {

    private String id;
    private String name;

    public CourseDTO(Course that) {
        this.id = that.getId();
        this.name = that.getName();
    }

}
