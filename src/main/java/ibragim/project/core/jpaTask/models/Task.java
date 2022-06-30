package ibragim.project.core.jpaTask.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tasks_table")
public class Task {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
}
