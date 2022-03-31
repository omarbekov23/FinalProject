package peaksoft.dto.mapper;

import org.springframework.stereotype.Component;
import peaksoft.dto.TeacherDto;
import peaksoft.models.Teacher;

@Component
public class TeacherMapper {
    public Teacher create(TeacherDto dto){
        if (dto==null){
            return null;
        }

        Teacher teacher = new Teacher();
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setEmail(dto.getEmail());

        return teacher;
    }
}
