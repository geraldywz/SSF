package ssf.todo.model;

import java.util.Objects;

public class Task {
    private String taskName;
    private boolean completed;

    public Task() {
    }

    public Task(String taskName, boolean completed) {
        this.taskName = taskName;
        this.completed = completed;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Task taskName(String taskName) {
        setTaskName(taskName);
        return this;
    }

    public Task completed(boolean completed) {
        setCompleted(completed);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(taskName, task.taskName) && completed == task.completed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, completed);
    }

    @Override
    public String toString() {
        return "{" +
            " taskName='" + getTaskName() + "'" +
            ", completed='" + isCompleted() + "'" +
            "}";
    }
}