package br.com.rqgr.srs.model;

import br.com.rqgr.infrastructure.AbstractEntity_;
import br.com.rqgr.srs.model.Course;
import br.com.rqgr.srs.model.Student;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-05-19T10:09:49", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(StudentCourse.class)
public class StudentCourse_ extends AbstractEntity_ {

    public static volatile SingularAttribute<StudentCourse, Student> studentId;
    public static volatile SingularAttribute<StudentCourse, Course> courseId;

}