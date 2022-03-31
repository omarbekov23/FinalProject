package peaksoft.models;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
@Getter @Setter
@RequiredArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    public String locatedCountry;

    @OneToMany(fetch=FetchType.EAGER ,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            mappedBy = "company")
    public List<Course> course;
}
