package ibragim.project.core.jpaTask.services.Impl;

import ibragim.project.core.jpaTask.models.Task;
import ibragim.project.core.jpaTask.models.Teacher;
import ibragim.project.core.jpaTask.repositories.TaskRepository;
import ibragim.project.core.jpaTask.repositories.TeachersRepository;
import ibragim.project.core.jpaTask.services.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeachersServiceImpl implements TeachersService {


    @Autowired
    TeachersRepository teachersRepository;
    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Teacher> getTeachers() {
        List<Teacher> teachers = teachersRepository.findAll();
        return teachers;
    }

    @Override
    public List<Teacher> getDifference(Long task_id) {
        Task t = taskRepository.findById(task_id).orElse(null);
        if(t!=null){
            List<Teacher> theList = teachersRepository.findAll();
            theList.removeAll(t.getTeachers());
            return theList;
        }
        return null;
    }
}
