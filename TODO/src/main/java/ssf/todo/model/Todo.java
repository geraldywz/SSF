package ssf.todo.model;

import java.io.Serializable;
import java.util.Objects;

public class Todo implements Serializable{
    private static final long serialVersionUID = -7817224776021728682L;
    private String task;

    public Todo() {
    }

    public Todo(String task) {
        this.task = task;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Todo task(String task) {
        setTask(task);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Todo)) {
            return false;
        }
        Todo todo = (Todo) o;
        return Objects.equals(task, todo.task);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(task);
    }

    @Override
    public String toString() {
        return "{" +
                " task='" + getTask() + "'" +
                "}";
    }

}