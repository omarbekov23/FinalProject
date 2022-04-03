package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.dto.CompanyDto;
import peaksoft.dto.mapper.CompanyMapper;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Company;
import peaksoft.models.Response;
import peaksoft.repositorys.CompanyRepository;
import peaksoft.services.CompanyService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Response saveCompany(CompanyDto dto) {
        String companyName = dto.getCompanyName();
        checkCompanyName(companyName);
        Company company = companyMapper.create(dto);
        Company saveCompany = companyRepository.save(company);
        return Response.builder()
                .httpStatus(CREATED)
                .message(String.format("company with companyName = %s successfully registered",
                        saveCompany.getCompanyName()))
                .build();
    }

    private void checkCompanyName(String companyName) {
        boolean exists = companyRepository.existsByCompanyName(companyName);
        if (exists) {
            log.warn("company with companyName = {} already exists", companyName);
            throw new BadRequestException(
                    "company with companyName = " + companyName + " already exists"
            );
        }
    }

    @Override
    public Response deleteById(Long id) {
        companyRepository.deleteById(id);
        log.info("Student with id = {} has successfully deleted", id);
        String message = String.format("Company with id = %s has successfully deleted", id);
        return Response.builder()
                .httpStatus(OK)
                .message(message)
                .build();
    }

    @Override
    public Company getById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("student with id = {} does not exists", id);
                    throw new NotFoundException(
                            String.format("student with id = %s does not exists", id)
                    );
                });
        log.info("founded student with id = {}", id);
        return company;
    }

    @Override
    public List<Company> findAllCompany() {
        List<Company> allCompany = companyRepository.findAll();
        log.info("founded {} company", allCompany.size());
        return allCompany;
    }

    @Override
    @Transactional
    public Response updateById(Long id, CompanyDto dto) {
        Company company = companyRepository.getById(id);

        String companyName = company.getCompanyName();
        String newCompanyName = dto.getCompanyName();
        if (!Objects.equals(companyName, newCompanyName)) {
            company.setCompanyName(newCompanyName);
            log.info("Company with id = {} changed name from {} to {}",
                    id, companyName, newCompanyName);
        }

        String locatedCountry = company.getLocatedCountry();
        String newLocatedCountry = dto.getLocatedCountry();

        if (!Objects.equals(locatedCountry, newLocatedCountry)) {
            company.setLocatedCountry(newLocatedCountry);
            log.info("Company with id = {} changed name from {} to {}",
                    id, locatedCountry, newLocatedCountry);
        }

        String message = String.format("Company with companyId = %s has successfully updated", id);
        return Response.builder()
                .httpStatus(RESET_CONTENT)
                .message(message)
                .build();
    }
}
