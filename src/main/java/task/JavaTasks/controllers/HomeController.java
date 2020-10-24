package task.JavaTasks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import task.JavaTasks.db.DBmanager;
import task.JavaTasks.db.Task;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
public class HomeController {
    @GetMapping(value="/")
    public String index(Model model ){
        ArrayList<Task> tasks = DBmanager.getTasks();
        model.addAttribute("tasks" , tasks);
        return "home";
    }

    @GetMapping(value="/filter")
    public  String filter(Model model ,
                          @RequestParam(name= "task_name" , defaultValue = "null" ) String name ,
                          @RequestParam(name = "deadline_from" , defaultValue = "null")String deadline_from,
                          @RequestParam(name = "deadline_to" , defaultValue = "null")String deadline_to,
                          @RequestParam(name = "completed" , defaultValue = "null")String completed) throws ParseException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!name.equals("null") || !deadline_from.equals("null") ||
                !deadline_to.equals("null") || !completed.equals("null")) {
            tasks = DBmanager.filter(name , deadline_from , deadline_to , completed);
        }
        model.addAttribute("tasks" , tasks);
        return  "home";
    }

    @GetMapping(value= "/details/{idishka}")
    public String details(Model model , @PathVariable(name = "idishka")Long id){
        Task task = DBmanager.getTask(id);
        model.addAttribute("task" , task);
        return "details";
    }


    @PostMapping(value= "/edit/{idishka}")
    public String edit(Model model , @PathVariable(name = "idishka")Long id,
                       @RequestParam(name= "name" ) String name ,
                       @RequestParam(name="description")String description,
                       @RequestParam(name = "date")String deadline,
                       @RequestParam(name = "completed")String completed
                       ){

        Task task = DBmanager.getTask(id);
        task.setName(name);
        task.setDescription(description);
        task.setDeadLineDate(deadline);
        if(completed.equals("true")){
            task.setCompleted(true);
        }
        else{
            task.setCompleted(false);
        }
        DBmanager.edit(task);
        model.addAttribute("task" , task);
        return "details";
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id")Long id){
        DBmanager.delete(id);
        return "redirect:/";
    }

    @PostMapping(value = "/addTask")
    public String addTask(@RequestParam(name = "item_name") String name ,
                          @RequestParam(name = "item_desc")String description ,
                          @RequestParam(name = "item_dead")String deadline ,
                          @RequestParam(name = "is_comp")String completed){

        Task task = new Task(null , name , description , deadline , false);
        DBmanager.addTask(task);
        return "redirect:/";
    }
}
