package peaksoft.services;

import peaksoft.dto.CompanyDto;
import peaksoft.models.Company;
import peaksoft.dto.response.Response;

import java.util.List;

public interface CompanyService {

    Response saveCompany(CompanyDto dto);

    Response deleteById(Long id);

    Company findById(long Id);

    List<Company> findAllCompany();

    Response updateById(Long id, CompanyDto dto);
}
