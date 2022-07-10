package ibragim.project.core.jpaTask.controllers;

import ibragim.project.core.jpaTask.models.Comment;
import ibragim.project.core.jpaTask.models.Task;
import ibragim.project.core.jpaTask.models.Teacher;
import ibragim.project.core.jpaTask.repositories.CommentsRepository;
import ibragim.project.core.jpaTask.repositories.TaskRepository;
import ibragim.project.core.jpaTask.repositories.TeachersRepository;
import ibragim.project.core.jpaTask.services.CommentService;
import ibragim.project.core.jpaTask.services.Impl.CommentServiceImpl;
import ibragim.project.core.jpaTask.services.TaskService;
import ibragim.project.core.jpaTask.services.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.attribute.standard.PresentationDirection;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CommentService commentService;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TeachersRepository teachersRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    TeachersService teachersService;

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
                          @RequestParam(name="commentary") String commentary) {
        Task task = new Task();
        task.setUserName(name);
        task.setCommentary(commentary);
        task.setCourseName(course);
        task.setPhoneNumber(number);

        Task theTask = taskService.addTask(task);

        return "redirect:/details/"+theTask.getId();
    }


    @GetMapping(value = "/details/{taskId}")
    public String taskDetails(@PathVariable(name = "taskId") Long id,
                              Model model){
        Task theTask = taskService.details(id);
        List <Comment> comments = commentService.getComments(id);
        List<Teacher> teachers = teachersService.getTeachers();
        List<Teacher> unChosenTeachers = teachersService.getDifference(theTask.getId());
        List<Teacher> chosenTeachers = theTask.getTeachers();
        if(comments!=null){
            model.addAttribute("comments", comments);
        }
        if(teachers!=null){
            model.addAttribute("teachers", teachers);
        }
        if(theTask!=null){
            model.addAttribute("task",theTask);
        }
        model.addAttribute("unChosenTeachers", unChosenTeachers);
        model.addAttribute("chosenTeachers", chosenTeachers);
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

    @PostMapping(value = "/addComment")
    public String addComment(@RequestParam(name = "teacher") Long teacherId,
                             @RequestParam(name = "task") Long taskId,
                             @RequestParam(name="comment") String commentText,
                             Model model){
        Comment comment = new Comment();
        comment.setText(commentText);
        commentService.addComment(comment, teacherId, taskId);
        System.out.println(taskId);
        System.out.println(teacherId);
        return "redirect:/details/"+taskId;
    }

    @PostMapping(value = "/addTeacherToTask")
    public String addTeacherToTask(@RequestParam(name = "teacher") Long teacher_id,
                                   @RequestParam(name = "task") Long task_id){

        Task t = taskService.addResponsibleTeacher(task_id, teacher_id);
        return "redirect:/details/"+t.getId();
    }


    @PostMapping(value = "/removeTeacherFromTask")
    public String removeTeacherFromTask(@RequestParam(name = "teacher") Long teacher_id,
                                        @RequestParam(name = "task") Long task_id){

        Task t = taskService.removeResponsibleTeacher(task_id, teacher_id);
        return "redirect:/details/"+t.getId();
    }
}
