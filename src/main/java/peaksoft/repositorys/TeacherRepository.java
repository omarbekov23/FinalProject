package peaksoft.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.models.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("select case when count(s) > 0 then true else false end " +
            "from Teacher s where s.email = ?1")
    boolean existsByEmail(String email);
}
