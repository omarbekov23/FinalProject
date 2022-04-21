package peaksoft.services;

import peaksoft.dto.CourseDto;
import peaksoft.models.Course;
import peaksoft.dto.response.Response;

import java.util.List;

public interface CourseService {

    Response saveCourse(CourseDto course, Long id);

    Response deleteById(Long id);

    Course getById(Long id);

    List<Course> findAllCourse();

    Response updateById(Long id, CourseDto course);
}
