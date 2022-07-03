package ibragim.project.core.jpaTask.repositories;

import ibragim.project.core.jpaTask.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentsRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.task.id=:task_id AND c.teacher.id=:teacher_id")
    Comment getComment(@Param("task_id") Long taskId,
                       @Param("teacher_id") Long teacherId);

    @Query("DELETE FROM Comment c WHERE c.task.id=:task_id")
    void deleteCommentByTaskId(@Param("task_id") Long taskId);
}
