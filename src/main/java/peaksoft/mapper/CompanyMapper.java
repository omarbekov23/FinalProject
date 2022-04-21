package peaksoft.mapper;

import org.springframework.stereotype.Component;
import peaksoft.dto.CompanyDto;
import peaksoft.models.Company;

@Component
public class CompanyMapper {

    public Company create(CompanyDto companyDto) {

        if (companyDto == null) {
            return null;

        }
        Company company = new Company();
        company.setCompanyName(companyDto.getCompanyName());
        company.setLocatedCountry(companyDto.getLocatedCountry());

        return company;
    }

}
