package peaksoft.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private String duration;

    @ManyToOne
    @JsonIgnore
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "courseList", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Group> groupList;

    @OneToOne(mappedBy = "course",
            cascade = CascadeType.REMOVE)
    private Teacher teacher;

    @JsonIgnore
    public void setGroup(Group group) {
        if (groupList == null) {
            groupList = new ArrayList<>();
        }
        groupList.add(group);
    }
}
