package peaksoft.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.CourseDto;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;

import peaksoft.models.Course;
import peaksoft.dto.response.Response;
import peaksoft.services.CourseService;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/course")
@AllArgsConstructor
public class CourseApi {

    private final CourseService courseService;

    @PostMapping("/save/{companyId}")
    public Response saveCourse(@RequestBody CourseDto course,@PathVariable("companyId") Long id) {
        return courseService.saveCourse(course,id);
    }

    @GetMapping("/getAll")
    public List<Course> findAllCourse() {
        return courseService.findAllCourse();
    }

    @GetMapping("/getById/{id}")
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
