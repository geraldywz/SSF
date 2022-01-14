package ssf.todo.model;

import java.util.Objects;

public class Task {
    private String name;
    private boolean completion;

    public Task() {
    }

    public Task(String name, boolean completion) {
        this.name = name;
        this.completion = completion;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompletion() {
        return this.completion;
    }

    public boolean getCompletion() {
        return this.completion;
    }

    public void setCompletion(boolean completion) {
        this.completion = completion;
    }

    public Task name(String name) {
        setName(name);
        return this;
    }

    public Task completion(boolean completion) {
        setCompletion(completion);
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
        return Objects.equals(name, task.name) && completion == task.completion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, completion);
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", completion='" + isCompletion() + "'" +
                "}";
    }

}