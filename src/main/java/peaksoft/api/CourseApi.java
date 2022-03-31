package peaksoft.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.CourseDto;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Course;
import peaksoft.models.Response;
import peaksoft.services.CompanyService;
import peaksoft.services.CourseService;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
public class CourseApi {

    private final CourseService courseService;
    private final CompanyService companyService;

    @PostMapping("/save/{companyId}")
    public Response save(@RequestBody CourseDto course, @PathVariable("companyId") Long companyId) {
        return courseService.saveCourse(course,companyId);
    }

    @GetMapping()
    public List<Course> findAllCourse() {
        return courseService.findAllCourse();
    }

    @GetMapping("/get/{id}")
    public Course getById(@PathVariable("id") Long id) {
        return courseService.getById(id);
    }

    @PatchMapping("/update/{id}")
    public Response update(@RequestBody CourseDto course, @PathVariable("id") Long id) {
        return courseService.updateById(id, course);
    }

    @DeleteMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Long id) {
        return courseService.deleteById(id);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public Response handleNotFoundException(NotFoundException notFoundException) {
        return Response.builder()
                .httpStatus(NOT_FOUND)
                .message(notFoundException.getMessage())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public Response handleBadRequestException(BadRequestException badRequestException) {
        return Response.builder()
                .httpStatus(BAD_REQUEST)
                .message(badRequestException.getMessage())
                .build();
    }
}
