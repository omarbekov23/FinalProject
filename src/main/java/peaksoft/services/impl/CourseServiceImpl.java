package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.dto.CourseDto;
import peaksoft.mapper.CourseMapper;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Course;
import peaksoft.dto.response.Response;
import peaksoft.repositories.CourseRepository;
import peaksoft.services.CompanyService;
import peaksoft.services.CourseService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper mapper;
    private final CompanyService service;

    @Override
    public Response saveCourse(CourseDto course, Long id) {
        Course course1 = mapper.create(course);
        course1.setCompany(service.findById(id));
        Course saveCourse = courseRepository.save(course1);
        return Response.builder()
                .httpStatus(CREATED)
                .message(String.format("course with courseName = %s successfully registered",
                        saveCourse.getCourseName()))
                .build();
    }

    @Override
    public Response deleteById(Long id) {
        courseRepository.deleteById(id);
        String message = String.format("Course with id = %s has successfully deleted", id);
        return Response.builder()
                .httpStatus(OK)
                .message(message)
                .build();
    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(
                            String.format("course with id = %s does not exists", id)
                    );
                });
    }

    @Override
    public List<Course> findAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional
    public Response updateById(Long id, CourseDto newCourse) {
        Course course = courseRepository.getById(id);

        String courseName = course.getCourseName();
        String newCourseName = newCourse.getCourseName();
        if (!Objects.equals(courseName, newCourseName)) {
            course.setCourseName(newCourseName);
        }

        String duration = course.getDuration();
        String newDuration = newCourse.getDuration();
        if (!Objects.equals(duration, newDuration)) {
            course.setDuration(newDuration);
        }

        String message = String.format("Course with companyId = %s has successfully updated", id);
        return Response.builder()
                .httpStatus(RESET_CONTENT)
                .message(message)
                .build();
    }
}
