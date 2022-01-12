package ssf.todo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ssf.todo.model.Todo;

public class TodoList {
    private static List<Todo> todoList = new ArrayList<>();

    public static void addTodo(Todo todo){
        todoList.add(todo);
    }

    public static List<Todo> getAll(){
        return todoList;
    }

    public static Todo generateTodo(){
        return new Todo(generateString(4, 8) + " " + generateString(4, 8) + " " + generateString(4, 8) + " " + generateString(4, 8));
    }

    private static String generateString(int min, int max) {
        Random random = new Random();
        int length = random.nextInt(max - min) + min;
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
    
}
