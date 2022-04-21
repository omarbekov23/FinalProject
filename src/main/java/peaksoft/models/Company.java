package peaksoft.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.MERGE;

@Entity
@Table(name = "company")
@Getter
@Setter
@RequiredArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String locatedCountry;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {MERGE, PERSIST, REMOVE},
            mappedBy = "company")
    public List<Course> course;
}
