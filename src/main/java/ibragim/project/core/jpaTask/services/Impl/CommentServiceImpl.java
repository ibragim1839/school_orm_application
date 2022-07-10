package ibragim.project.core.jpaTask.services.Impl;

import ibragim.project.core.jpaTask.models.Comment;
import ibragim.project.core.jpaTask.models.Task;
import ibragim.project.core.jpaTask.models.Teacher;
import ibragim.project.core.jpaTask.repositories.CommentsRepository;
import ibragim.project.core.jpaTask.repositories.TaskRepository;
import ibragim.project.core.jpaTask.repositories.TeachersRepository;
import ibragim.project.core.jpaTask.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TeachersRepository teachersRepository;

    @Override
    public Comment addComment(Comment comment, Long teacher_id, Long task_id) {
        Teacher teacher = teachersRepository.findById(teacher_id).orElse(null);
        Task task = taskRepository.findById(task_id).orElse(null);
        if(teacher!=null && task!=null){
            comment.setTask(task);
            comment.setTeacher(teacher);
            return commentsRepository.save(comment);
        }
        return null;


    }

    @Override
    public List<Comment> getComments(Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if(task!=null){
            List<Comment> comments =  commentsRepository.findAllByTask(task);
            return comments;
        }
        return null;
    }

}
