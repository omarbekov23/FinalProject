package peaksoft.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.TeacherDto;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.dto.response.Response;
import peaksoft.models.Teacher;
import peaksoft.services.CourseService;
import peaksoft.services.TeacherService;

import java.util.List;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/teacher")
@AllArgsConstructor
public class TeacherApi {

    private final TeacherService teacherService;
    private final CourseService courseService;

    @PostMapping("/save/{courseId}")
    public Response saveGroup(@RequestBody TeacherDto teacher,@PathVariable("courseId") Long id){
        return teacherService.saveTeacher(teacher,id);
    }

    @GetMapping("/getAll")
    public List<Teacher> findAllTeacher(){
        return teacherService.findAllTeacher();
    }

    @GetMapping("/getById/{id}")
    public Teacher getById(@PathVariable("id")Long id){
        return teacherService.getById(id);
    }

    @PatchMapping("/update/{id}")
    public Response update(@RequestBody TeacherDto teacher, @PathVariable("id") Long id) {
        return teacherService.updateById(id, teacher);
    }

    @DeleteMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Long id) {
        return teacherService.deleteById(id);
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
