package peaksoft.services;

import peaksoft.dto.StudentDto;
import peaksoft.dto.response.Response;
import peaksoft.models.Student;

import java.util.List;

public interface StudentService {
    Response saveStudent(StudentDto student,Long id);

    Response deleteById(Long id);

    Student getById(Long id);

    List<Student> findAllStudent();

    Response updateById(Long id, StudentDto student);
}
