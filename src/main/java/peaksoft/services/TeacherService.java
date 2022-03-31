package peaksoft.services;

import peaksoft.dto.TeacherDto;
import peaksoft.models.Response;
import peaksoft.models.Teacher;

import java.util.List;

public interface TeacherService {
    Response saveTeacher(TeacherDto teacher,Long id);

    Response deleteById(Long id);

    Teacher getById(Long id);

    List<Teacher> findAllTeacher();

    Response updateById(Long id, TeacherDto teacher);
}
