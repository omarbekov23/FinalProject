package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.dto.TeacherDto;
import peaksoft.dto.mapper.TeacherMapper;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Response;
import peaksoft.models.Teacher;
import peaksoft.repositorys.TeacherRepository;
import peaksoft.services.CourseService;
import peaksoft.services.TeacherService;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseService service;
    private final TeacherMapper mapper;

    @Override
    public Response saveTeacher(TeacherDto teacher, Long id) {
        String email = teacher.getEmail();
        checkGroupName(email);
        Teacher teacher1 = mapper.create(teacher);
        teacher1.setCourse(service.getById(id));
        Teacher saveTeacher = teacherRepository.save(teacher1);
        log.info("Teacher with email = {} has successfully saved to database", saveTeacher.getEmail());
        return Response.builder()
                .httpStatus(CREATED)
                .message(String.format("Teacher with email = %s successfully registered",
                        saveTeacher.getEmail()))
                .build();
    }

    private void checkGroupName(String email) {
        boolean exists = teacherRepository.existsByEmail(email);
        if (exists) {
            log.warn("teacher with Teacher Email = {} already exists", email);
            throw new BadRequestException(
                    "teacher with Teacher Email = " + email + " already exists"
            );
        }
    }

    @Override
    public Response deleteById(Long id) {
        teacherRepository.deleteById(id);
        log.info("Teacher with id = {} has successfully deleted", id);
        String message = String.format("Teacher with id = %s has successfully deleted", id);
        return Response.builder()
                .httpStatus(OK)
                .message(message)
                .build();
    }

    @Override
    public Teacher getById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("teacher with id = {} does not exists", id);
                    throw new NotFoundException(
                            String.format("teacher with id = %s does not exists", id)
                    );
                });
        log.info("founded teacher with id = {}", id);
        return teacher;
    }

    @Override
    public List<Teacher> findAllTeacher() {
        List<Teacher> allTeacher = teacherRepository.findAll();
        log.info("founded {} teacher", allTeacher.size());
        return allTeacher;
    }

    @Override
    @Transactional
    public Response updateById(Long id, TeacherDto newTeacher) {
        Teacher teacher = teacherRepository.getById(id);

        String firstName = teacher.getFirstName();
        String newFirstName = newTeacher.getFirstName();
        if (!Objects.equals(firstName, newFirstName)) {
            teacher.setFirstName(newFirstName);
            log.info("Teacher with id = {} changed name from {} to {}",
                    id, firstName, newFirstName);
        }

        String lastName = teacher.getLastName();
        String newLastName = newTeacher.getLastName();
        if (!Objects.equals(lastName, newLastName)) {
            teacher.setLastName(newLastName);
            log.info("Teacher with id = {} changed name from {} to {}",
                    id, lastName, newLastName);
        }
        String email = teacher.getEmail();
        String newEmail = newTeacher.getEmail();
        if (!Objects.equals(email, newEmail)) {
            teacher.setEmail(newEmail);
            log.info("Teacher with id = {} changed name from {} to {}",
                    id, email, newEmail);
        }

        String message = String.format("Teacher with teacherId = %s has successfully updated", id);
        return Response.builder()
                .httpStatus(RESET_CONTENT)
                .message(message)
                .build();
    }
}
