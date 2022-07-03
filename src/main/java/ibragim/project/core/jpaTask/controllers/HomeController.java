package ibragim.project.core.jpaTask.controllers;

import ibragim.project.core.jpaTask.models.Comment;
import ibragim.project.core.jpaTask.models.Task;
import ibragim.project.core.jpaTask.models.Teacher;
import ibragim.project.core.jpaTask.repositories.CommentsRepository;
import ibragim.project.core.jpaTask.repositories.TaskRepository;
import ibragim.project.core.jpaTask.repositories.TeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {


    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TeachersRepository teachersRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @GetMapping(value = "/")
    public String getMainPage(Model model){
        List<Task> allTasks = taskRepository.findAll();
        model.addAttribute("allTasks", allTasks);
        return "mainPage";
    }

    @GetMapping(value = "/handledTasks")
    public String getHandledTasks(Model model){
        List<Task> handledTasks = taskRepository.findAllByHandledIsTrue();
        model.addAttribute("handledTasks", handledTasks);
        return "handledTasksPage";
    }

    @GetMapping(value = "/notHandledTasks")
    public String getNotHandledTasks(Model model){
        List<Task> notHandledTasks = taskRepository.findAllByHandledIsFalse();
        model.addAttribute("notHandledTasks", notHandledTasks);
        return "notHandledTasksPage";
    }

    @GetMapping(value = "/addTask")
    public String addTask(Model model){
        List<Teacher> teachers = teachersRepository.findAll();
        model.addAttribute("teachers", teachers);
        return "addTaskPage";
    }

    @PostMapping(value = "/addTask")
    public String addTask(@RequestParam(name = "name") String name,
                          @RequestParam(name = "course") String course,
                          @RequestParam(name="number") String number,
                          @RequestParam(name="commentary") String commentary,
                          @RequestParam(name = "teacher") Long teacher_id) {

        Teacher teacher = teachersRepository.findById(teacher_id).orElse(null);

        if(teacher!=null){
            Task task = new Task();
            task.setUserName(name);
            task.setCourseName(course);
            task.setCommentary(commentary);
            task.setPhoneNumber(number);
            task.setHandled(false);
            task.setTeacher(teacher);
            taskRepository.save(task);
        }


        return "redirect:/";
    }


    @GetMapping(value = "/details/{taskId}")
    public String taskDetails(@PathVariable(name = "taskId") Long id,
                              Model model){
        Task theTask = taskRepository.findById(id).orElse(null);
        model.addAttribute("task",theTask);

        Comment comment = commentsRepository.getComment(theTask.getId(), theTask.getTeacher().getId());
        if(comment!=null){
            model.addAttribute("comment", comment);
        }
        return "details";

    }

    @PostMapping(value = "/handelTask")
    public String handelTask(@RequestParam(name = "id") Long id){
        Task theTask = taskRepository.findById(id).orElse(null);
        theTask.setHandled(true);
        taskRepository.save(theTask);
        return "redirect:/";
    }

    @PostMapping(value = "/deleteTask")
    public String deleteTask(@RequestParam(name="id") Long id){
        try{
            commentsRepository.deleteCommentByTaskId(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        taskRepository.deleteById(id);
        return "redirect:/";
    }
}
