package peaksoft.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CompanyDto {

    @NotBlank
    private String companyName;

    @NotBlank
    private String locatedCountry;
}
