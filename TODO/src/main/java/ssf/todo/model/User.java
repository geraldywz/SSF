package ssf.todo.model;

import java.util.List;
import java.util.Objects;

public class User {
    private String name;
    private List<Task> todoList;

    public User() {
    }

    public User(String name, List<Task> todoList) {
        this.name = name;
        this.todoList = todoList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTodoList() {
        return this.todoList;
    }

    public void setTodoList(List<Task> todoList) {
        this.todoList = todoList;
    }

    public User name(String name) {
        setName(name);
        return this;
    }

    public User todoList(List<Task> todoList) {
        setTodoList(todoList);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(todoList, user.todoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, todoList);
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", todoList='" + getTodoList() + "'" +
                "}";
    }

}