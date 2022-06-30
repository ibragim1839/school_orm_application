package ibragim.project.core.jpaTask.repositories;

import ibragim.project.core.jpaTask.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findAllByHandledIsTrue();
    public List<Task> findAllByHandledIsFalse();
}
