package ibragim.project.core.jpaTask.services;

import ibragim.project.core.jpaTask.models.Comment;

import java.security.PublicKey;
import java.util.List;

public interface CommentService {

    public Comment addComment(Comment comment, Long teacher_id, Long task_id);

    public List<Comment> getComments(Long taskId);
}
