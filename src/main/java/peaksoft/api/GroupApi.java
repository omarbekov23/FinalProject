package peaksoft.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.GroupDto;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Group;
import peaksoft.dto.response.Response;
import peaksoft.services.CourseService;
import peaksoft.services.GroupService;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@AllArgsConstructor
@RequestMapping("/api/group")
public class GroupApi {

    private final GroupService groupService;
    private final CourseService courseService;

    @PostMapping("/save/{courseId}")
    public Response saveGroup(@RequestBody GroupDto group, @PathVariable("courseId") Long id){
        return groupService.saveGroup(group,id);
    }

    @GetMapping("/getAll")
    public List<Group> findAllGroup(){
        return groupService.findAllGroup();
    }

    @GetMapping("/getById{id}")
    public Group getById(@PathVariable("id")Long id){
        return groupService.getById(id);
    }

    @PatchMapping("/update/{id}")
    public Response update(@RequestBody GroupDto group, @PathVariable("id") Long id) {
        return groupService.updateById(id,group);
    }

    @DeleteMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Long id) {
        return groupService.deleteById(id);
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
