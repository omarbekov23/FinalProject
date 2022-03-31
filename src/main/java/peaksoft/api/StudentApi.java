package peaksoft.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.StudentDto;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Response;
import peaksoft.models.Student;
import peaksoft.services.GroupService;
import peaksoft.services.StudentService;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentApi {
    private final StudentService studentService;
    private final GroupService groupService;

    @PostMapping("/save/{groupId}")
    public Response saveGroup(@RequestBody StudentDto student,@PathVariable("groupId") Long id){
        return studentService.saveStudent(student,id);
    }

    @GetMapping("/getAll")
    public List<Student> findAllStudent(){
        return studentService.findAllStudent();
    }

    @GetMapping("/get/{id}")
    public Student getById(@PathVariable("id")Long id){
        return studentService.getById(id);
    }

    @PatchMapping("/update/{id}")
    public Response update(@RequestBody StudentDto student, @PathVariable("id") Long id) {
        return studentService.updateById(id, student);
    }

    @DeleteMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Long id) {
        return studentService.deleteById(id);
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
