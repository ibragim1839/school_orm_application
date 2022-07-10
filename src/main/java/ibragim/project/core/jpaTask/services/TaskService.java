package ibragim.project.core.jpaTask.services;

import ibragim.project.core.jpaTask.models.Task;


public interface TaskService {

    public Task addTask(Task task);

    public Task details(Long id);

    public Task addResponsibleTeacher( Long task_id, Long teacher_id);

    public Task removeResponsibleTeacher( Long task_id, Long teacher_id);


}
