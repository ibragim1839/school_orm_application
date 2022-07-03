package ibragim.project.core.jpaTask.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name="t_tasks")
@NoArgsConstructor
public class Task {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY )
    @Column(name = "id")
    Long id;

    @Column(name="user_name")
    String userName;

    @Column(name="course_name")
    String courseName;

    @Column(name="commentary", columnDefinition = "TEXT")
    String commentary;

    @Column(name="phone_naumber")
    String phoneNumber;

    @Column(name="handled")
    Boolean handled;

    @ManyToOne(fetch = FetchType.EAGER)
    private Teacher teacher;

}
