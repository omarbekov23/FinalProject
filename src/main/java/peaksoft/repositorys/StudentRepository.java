package peaksoft.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select case when count(s) > 0 then true else false end " +
            "from Student s where s.email = ?1")
    boolean existsByEmail(String email);
}
