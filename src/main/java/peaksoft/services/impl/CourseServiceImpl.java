package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.dto.CourseDto;
import peaksoft.dto.mapper.CourseMapper;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Course;
import peaksoft.models.Response;
import peaksoft.repositorys.CompanyRepository;
import peaksoft.repositorys.CourseRepository;
import peaksoft.services.CompanyService;
import peaksoft.services.CourseService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper mapper;
    private final CompanyService service;

    @Override
    public Response saveCourse(CourseDto course,Long id) {
        String courseName = course.getCourseName();
        checkCourseName(courseName);
        Course course1 = mapper.create(course);
        course1.setCompany(service.getById(id));
        Course saveCourse = courseRepository.save(course1);
        log.info("Course with courseName = {} has sucessfully saved to database", saveCourse.getCourseName());
        return Response.builder()
                .httpStatus(CREATED)
                .message(String.format("course with courseName = %s successfully registered",
                        saveCourse.getCourseName()))
                .build();
    }

    private void checkCourseName(String courseName) {
        boolean exists = courseRepository.existsByCourseName(courseName);
        if (exists) {
            log.warn("course with courseName = {} already exists", courseName);
            throw new BadRequestException(
                    "course with courseName = " + courseName + " already exists"
            );
        }
    }

    @Override
    public Response deleteById(Long id) {
        courseRepository.deleteById(id);
        log.info("Course with id = {} has successfully deleted", id);
        String message = String.format("Course with id = %s has successfully deleted", id);
        return Response.builder()
                .httpStatus(OK)
                .message(message)
                .build();
    }

    @Override
    public Course getById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("student with id = {} does not exists", id);
                    throw new NotFoundException(
                            String.format("student with id = %s does not exists", id)
                    );
                });
        log.info("founded student with id = {}", id);
        return course;
    }

    @Override
    public List<Course> findAllCourse() {
        List<Course> allCourse = courseRepository.findAll();
        log.info("founded {} course", allCourse.size());
        return allCourse;
    }

    @Override
    @Transactional
    public Response updateById(Long id, CourseDto newCourse) {
        Course course = courseRepository.getById(id);

        String courseName = course.getCourseName();
        String newCourseName = newCourse.getCourseName();
        if (!Objects.equals(courseName, newCourseName)) {
            course.setCourseName(newCourseName);
            log.info("Course with id = {} changed name from {} to {}",
                    id, courseName, newCourseName);
        }

        String duration = course.getDuration();
        String newDuration = newCourse.getDuration();
        if (!Objects.equals(duration, newDuration)) {
            course.setDuration(newDuration);
            log.info("Course with id = {} changed name from {} to {}",
                    id, duration, newDuration);
        }

        String message = String.format("Course with companyId = %s has successfully updated", id);
        return Response.builder()
                .httpStatus(RESET_CONTENT)
                .message(message)
                .build();
    }
}
