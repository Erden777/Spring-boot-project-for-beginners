package task.JavaTasks.db;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Long id;
    private String name;
    private String description;
    private String deadLineDate;
    private boolean isCompleted;
}
