package ibragim.project.core.jpaTask.services.Impl;

import ibragim.project.core.jpaTask.models.Teacher;
import ibragim.project.core.jpaTask.repositories.TaskRepository;
import ibragim.project.core.jpaTask.repositories.TeachersRepository;
import ibragim.project.core.jpaTask.services.CommentService;
import ibragim.project.core.jpaTask.services.TaskService;
import ibragim.project.core.jpaTask.services.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import ibragim.project.core.jpaTask.models.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TeachersRepository teachersRepository;

    @Override
    public Task addTask(Task task) {
        if (task != null) {
            task.setHandled(false);
            return taskRepository.save(task);
        }
        return null;
    }

    @Override
    public Task details(Long id) {
        Task theTask = taskRepository.findById(id).orElse(null);
        if(theTask!=null){
            return theTask;
        }
        return null;
    }

    @Override
    public Task addResponsibleTeacher(Long task_id, Long teacher_id) {
        Task theTask = taskRepository.findById(task_id).orElse(null);
        Teacher theTeacher = teachersRepository.findById(teacher_id).orElse(null);
        if(theTask != null){
            if(theTask.getTeachers()==null){
                List <Teacher> teachers = new ArrayList<>();
                theTask.setTeachers(teachers);
            }
            if(theTeacher!=null){
                List<Teacher> teachers =theTask.getTeachers();
                teachers.add(theTeacher);
                theTask.setTeachers(teachers);
                return taskRepository.save(theTask);
            }
        }
        return null;
    }

    @Override
    public Task removeResponsibleTeacher(Long task_id, Long teacher_id) {
        Task task = taskRepository.findById(task_id).orElse(null);
        if(task!=null){
            List <Teacher> teachers = task.getTeachers();
            if(teachers!=null){
                Teacher teacher = teachersRepository.findById(teacher_id).orElse(null);
                if(teacher!=null){
                    teachers.remove(teacher);
                    task.setTeachers(teachers);
                    return taskRepository.save(task);
                }
            }
        }
       return null;
    }
}
