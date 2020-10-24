package task.JavaTasks.db;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DBmanager {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Long id = 5L;

    static{
        tasks.add(new Task(1L ,"Task 1" , "this is task 1 " , "2020-10-02" , false));
        tasks.add(new Task(2L ,"Task 2" , "this is task 2 " , "2020-09-12" , false));
        tasks.add(new Task(3L ,"Task 3" , "this is task 3 " , "2020-08-22" , false));
        tasks.add(new Task(4L ,"Task 4" , "this is task 4 " , "2020-10-21" , false));
    }

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static void addTask(Task task) {
        task.setId(id);
        tasks.add(task);
        id++;
    }

    public static Task getTask(Long id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }




    public static ArrayList<Task> filter(String name,String deadlineFrom,String deadlineTo,String completed) throws ParseException {
        ArrayList<Task> t = new ArrayList<>();
        boolean c = false;
        if (completed != null) {
            if(completed.equals("true")){
                c= true;
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(Task ts :tasks) {
            Date simple = dateFormat.parse(ts.getDeadLineDate());
            if(!name.equals("null") ){
                if(ts.getName().contains(name) && ts.isCompleted()==c ){
                    if (deadlineFrom.equals("null") && deadlineTo.equals("null")) {
                        t.add(ts);
                    } else {
                        if (!deadlineFrom.equals("null") && deadlineTo.equals("null")) {
                            if(simple.after(dateFormat.parse(deadlineFrom))){
                                t.add(ts);
                            }
                        } else if (deadlineFrom.equals("null")) {
                            if(simple.before(dateFormat.parse(deadlineTo))){
                                t.add(ts);
                            }
                        } else {
                            if (simple.after(dateFormat.parse(deadlineFrom)) && simple.before(dateFormat.parse(deadlineTo))) {
                                t.add(ts);
                            }
                        }
                    }
                }

            } else if (ts.isCompleted() == c) {
                if (deadlineFrom.equals("null") && deadlineTo.equals("null")) {
                    t.add(ts);
                }else {
                    if (!deadlineFrom.equals("null") && deadlineTo.equals("null")) {
                        if (simple.after(dateFormat.parse(deadlineFrom))) {
                            t.add(ts);
                        }
                    } else if (deadlineFrom.equals("null")) {
                        if (simple.before(dateFormat.parse(deadlineTo))) {
                            t.add(ts);
                        }
                    } else {
                        if (simple.after(dateFormat.parse(deadlineFrom)) && simple.before(dateFormat.parse(deadlineTo))) {
                            t.add(ts);
                        }
                    }
                }
            }
        }
        return t;
    }

    public static void edit(Task task){
        for (Task t : tasks) {
            if(t.getId().equals(task.getId())){
                t = task;
            }
        }
    }

    public static void delete(Long id) {
        for(Task t : tasks){
            if(t.getId().equals(id)){
                tasks.remove(t);
                break;
            }
        }
    }
}
