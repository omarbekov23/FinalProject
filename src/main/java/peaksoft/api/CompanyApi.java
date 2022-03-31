package peaksoft.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.CompanyDto;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Company;
import peaksoft.models.Response;
import peaksoft.services.CompanyService;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@AllArgsConstructor
@RequestMapping("/api/company")
public class CompanyApi {

    private final CompanyService service;

    @GetMapping()
    public List<Company> getAllCompany() {
        return service.findAllCompany();
    }

    @PostMapping()
    public Response saveCompany(@RequestBody CompanyDto company) {
        return service.saveCompany(company);
    }

    @GetMapping("{id}")
    public Company getById(@PathVariable("id") Long id) {
        return (service.getById(id));
    }

    @PatchMapping("{id}")
    public Response update(@RequestBody CompanyDto company, @PathVariable("id") Long id) {
        return service.updateById(id, company);
    }

    @DeleteMapping("{id}")
    public Response delete(@PathVariable("id") Long id) {
       return service.deleteById(id);
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public Response handleNotFoundException(NotFoundException notFoundException) {
        return Response.builder()
                .httpStatus(NOT_FOUND)
                .message(notFoundException.getMessage())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public Response handleBadRequestException(BadRequestException badRequestException) {
        return Response.builder()
                .httpStatus(BAD_REQUEST)
                .message(badRequestException.getMessage())
                .build();
    }
}
