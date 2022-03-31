package peaksoft.dto.mapper;

import org.springframework.stereotype.Component;
import peaksoft.dto.CourseDto;
import peaksoft.models.Course;

@Component
public class CourseMapper {

    public Course create(CourseDto dto){
        if (dto == null){
            return null;
        }
        Course course = new Course();
        course.setCourseName(dto.getCourseName());
        course.setDuration(dto.getDuration());
        return course;
    }
}
