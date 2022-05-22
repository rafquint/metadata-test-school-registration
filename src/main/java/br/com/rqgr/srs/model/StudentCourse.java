package br.com.rqgr.srs.model;

import br.com.rqgr.infrastructure.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_course",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"student_id", "course_id"})
        }
)
@XmlRootElement
public class StudentCourse extends AbstractEntity {

    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;

    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Student student;

}
