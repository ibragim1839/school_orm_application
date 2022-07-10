package ibragim.project.core.jpaTask.services;

import ibragim.project.core.jpaTask.models.Teacher;

import java.util.List;

public interface TeachersService {

    List<Teacher> getTeachers();

    List<Teacher> getDifference(Long task_id);
}
