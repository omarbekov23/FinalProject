package peaksoft.dto.mapper;

import org.springframework.stereotype.Component;
import peaksoft.dto.StudentDto;
import peaksoft.models.Student;

@Component
public class StudentMapper {
    public Student create(StudentDto dto){

        if (dto==null){
            return null;
        }
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setStudyFormat(dto.getStudyFormat());

        return student;
    }
}
