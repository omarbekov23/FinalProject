package peaksoft.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;

    private String dateOfStart;

    private String dateOfFinish;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Course> courseList;

    @OneToMany(mappedBy = "groups", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Student> students;

    @JsonIgnore
    public void setCourse(Course courseId) {
        if (courseList == null) {
            courseList = new ArrayList<>();
        }
        courseList.add(courseId);
        courseId.setGroup(this);
    }
}

