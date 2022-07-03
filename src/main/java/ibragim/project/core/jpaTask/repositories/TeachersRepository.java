package ibragim.project.core.jpaTask.repositories;

import ibragim.project.core.jpaTask.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TeachersRepository extends JpaRepository<Teacher, Long> {
}
