package todo.codepath.piyush6348.com.codepathtodo.model;

/**
 * Created by dell on 2/25/2017.
 */

public class ToDoItem {
    private String title,description;
    private int priority;
    private long time;
    private String date;

    public ToDoItem(String title, String description, int priority, long time, String date) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.time = time;
        this.date=date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public long getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
