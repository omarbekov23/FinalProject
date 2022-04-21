package peaksoft.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.models.enam.StudyFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StudentDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    private StudyFormat studyFormat;

}
