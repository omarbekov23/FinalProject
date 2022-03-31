package peaksoft.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select case when count(s) > 0 then true else false end " +
            "from Company s where s.companyName = ?1")
    boolean existsByCompanyName(String companyName);

}
