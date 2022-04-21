package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.dto.StudentDto;
import peaksoft.mapper.StudentMapper;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.dto.response.Response;
import peaksoft.models.Student;
import peaksoft.models.enam.StudyFormat;
import peaksoft.repositories.StudentRepository;
import peaksoft.services.GroupService;
import peaksoft.services.StudentService;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final GroupService service;
    private final StudentMapper mapper;

    @Override
    public Response saveStudent(StudentDto student,Long id) {
        String email = student.getEmail();
        checkGroupName(email);
        Student student1 = mapper.create(student);
        student1.setGroups(service.getById(id));
        Student saveStudent = studentRepository.save(student1);
        log.info("Student with email = {} has successfully saved to database", saveStudent.getEmail());
        return Response.builder()
                .httpStatus(CREATED)
                .message(String.format("Group with groupName = %s successfully registered",
                        saveStudent.getEmail()))
                .build();
    }

    private void checkGroupName(String email) {
        boolean exists = studentRepository.existsByEmail(email);
        if (exists) {
            log.warn("group with Student Email = {} already exists", email);
            throw new BadRequestException(
                    "group with Student Email = " + email + " already exists"
            );
        }
    }

    @Override
    public Response deleteById(Long id) {
        studentRepository.deleteById(id);
        log.info("Student with id = {} has successfully deleted", id);
        String message = String.format("Student with id = %s has successfully deleted", id);
        return Response.builder()
                .httpStatus(OK)
                .message(message)
                .build();
    }

    @Override
    public Student getById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("group with id = {} does not exists", id);
                    throw new NotFoundException(
                            String.format("group with id = %s does not exists", id)
                    );
                });
        log.info("founded group with id = {}", id);
        return student;
    }

    @Override
    public List<Student> findAllStudent() {
        List<Student> allStudent = studentRepository.findAll();
        log.info("founded {} students", allStudent.size());
        return allStudent;
    }

    @Override
    @Transactional
    public Response updateById(Long id, StudentDto newStudent) {
        Student student = studentRepository.getById(id);

        String firstName = student.getFirstName();
        String newFirstName = newStudent.getFirstName();
        if (!Objects.equals(firstName, newFirstName)) {
            student.setFirstName(newFirstName);
            log.info("Student with id = {} changed name from {} to {}",
                    id, firstName, newFirstName);
        }

        String lastName = student.getLastName();
        String newLastName = newStudent.getLastName();
        if (!Objects.equals(lastName, newLastName)) {
            student.setLastName(newLastName);
            log.info("Student with id = {} changed name from {} to {}",
                    id, lastName, newLastName);
        }
        String email = student.getEmail();
        String newEmail = newStudent.getEmail();
        if (!Objects.equals(email, newEmail)) {
            student.setEmail(newEmail);
            log.info("Student with id = {} changed name from {} to {}",
                    id, email, newEmail);
        }
        StudyFormat studyFormat = student.getStudyFormat();
        StudyFormat newStudyFormat = newStudent.getStudyFormat();
        if (!Objects.equals(studyFormat, newStudyFormat)) {
            student.setStudyFormat(newStudyFormat);
            log.info("Student with id = {} changed name from {} to {}",
                    id, studyFormat, newStudyFormat);
        }

        String message = String.format("Student with studentId = %s has successfully updated", id);
        return Response.builder()
                .httpStatus(RESET_CONTENT)
                .message(message)
                .build();
    }
}
