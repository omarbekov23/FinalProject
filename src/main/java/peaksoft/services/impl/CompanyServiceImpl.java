package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.dto.CompanyDto;
import peaksoft.mapper.CompanyMapper;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Company;
import peaksoft.dto.response.Response;
import peaksoft.repositories.CompanyRepository;
import peaksoft.services.CompanyService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Response saveCompany(CompanyDto dto) {
        Company company = companyMapper.create(dto);
        Company saveCompany = companyRepository.save(company);
        return Response.builder()
                .httpStatus(CREATED)
                .message(String.format("company with companyName = %s successfully registered",
                        saveCompany.getCompanyName()))
                .build();
    }

    @Override
    public Response deleteById(Long id) {
        companyRepository.deleteById(id);
        String message = String.format("Company with id = %s has successfully deleted", id);
        return Response.builder()
                .httpStatus(OK)
                .message(message)
                .build();
    }

    @Override
    public Company findById(long Id) {
        return companyRepository.findById(Id)
                .orElseThrow(() -> {
                    throw new NotFoundException(
                            String.format("Company with id = %s does not exists", Id)
                    );
                });
    }

    @Override
    public List<Company> findAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    @Transactional
    public Response updateById(Long id, CompanyDto dto) {
        Company company = companyRepository.getById(id);

        String companyName = company.getCompanyName();
        String newCompanyName = dto.getCompanyName();
        if (!Objects.equals(companyName, newCompanyName)) {
            company.setCompanyName(newCompanyName);
        }

        String locatedCountry = company.getLocatedCountry();
        String newLocatedCountry = dto.getLocatedCountry();

        if (!Objects.equals(locatedCountry, newLocatedCountry)) {
            company.setLocatedCountry(newLocatedCountry);
        }

        String message = String.format("Company with companyId = %s has successfully updated", id);
        return Response.builder()
                .httpStatus(RESET_CONTENT)
                .message(message)
                .build();
    }
}
