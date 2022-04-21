package peaksoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
