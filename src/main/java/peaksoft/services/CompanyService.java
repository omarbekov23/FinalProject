package peaksoft.services;

import peaksoft.dto.CompanyDto;
import peaksoft.models.Company;
import peaksoft.models.Response;

import java.util.List;

public interface CompanyService {

    Response saveCompany(CompanyDto dto);

    Response deleteById(Long id);

    Company getById(Long id);

    List<Company> findAllCompany();

    Response updateById(Long id, CompanyDto dto);
}
