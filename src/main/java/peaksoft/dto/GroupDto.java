package peaksoft.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GroupDto {

    @NotBlank
    private String groupName;

    @NotBlank
    private String dateOfStart;

    @NotBlank
    private String dateOfFinish;
}
