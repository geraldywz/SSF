package ssf.todo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ssf.todo.model.Task;
import ssf.todo.model.Todo;
import ssf.todo.model.User;

public class Todoist {
    private static List<String> userList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Todoist.class);

    public static User getUser(String name) {
        User userFound = null;
        for (String json : userList) {
            User user = toUser(json).get();
            if (name.equals(user.getName())) {
                userFound = user;
                break;
            }
        }
        return userFound;
    }

    public static List<String> getUsernames() {
        List<String> userNames = new ArrayList<>();
        for (String json : userList) {
            User user = toUser(json).get();
            userNames.add(user.getName());
        }
        return userNames;
    }

    public static void saveUser(User user) {
        userList.add(toJSON(user).get());
    }

    private static Optional<String> toJSON(User user) {
        ObjectMapper objectmapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectmapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(jsonString);
    }

    private static Optional<User> toUser(String json) {
        ObjectMapper objectmapper = new ObjectMapper();
        User user = null;
        try {
            user = objectmapper.readValue(json, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(user);
    }

    public static Task generateTask() {
        List<String> tasks = Arrays.asList(
                "Take a walk.",
                "Walk the dog.",
                "Pet the cat.",
                "Feed the baby.",
                "Sweep the floor.",
                "Bake a cake.",
                "Buy some groceries.",
                "Smell the flowers.",
                "Water the plants.",
                "Ride a bike.",
                "Hit the gym.",
                "Get a haircut.",
                "Save the world.",
                "Write a book.",
                "Finish your homework.",
                "Fly a kite.",
                "Seize the day.",
                "Climb a tree.",
                "Take a swing.",
                "Count the clouds.",
                "Swim 20 laps.");
        Collections.shuffle(tasks);
        return new Task(tasks.get(0), false);
    }

    public static void generateUsers() {
        if (userList.size() == 0) {
            List<String> users = Arrays.asList(
                    "Adam", "Bob", "Charlie", "Don", "Ernest", "Fred", "Gordon", "Henry", "Ivan", "John");
            Collections.shuffle(users);
            int numUsers = generateNumber(3, 5);
            int userIndex = 0;
            while (userIndex < numUsers) {
                int numTask = generateNumber(4, 6);
                List<Task> tasks = new ArrayList<>();
                int taskIndex = 0;
                while (taskIndex < numTask) {
                    tasks.add(generateTask());
                    taskIndex++;
                }
                String newUser = toJSON(new User(users.get(userIndex), tasks)).get();
                logger.info("New user generated >>>>> " + newUser);
                userList.add(newUser);
                userIndex++;
            }
        }
    }

    private static int generateNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private static String generateString(int min, int max) {
        Random random = new Random();
        int length = generateNumber(min, max);
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    // =================== LINE OF DEPRECATION ================

    public static synchronized String generateKey() {
        Random random = new Random();
        HexFormat hex = HexFormat.of().withUpperCase();
        String newID = hex.toHexDigits(random.nextInt());
        return newID;
    }

    private static List<Todo> todoList = new ArrayList<>();

    public static void addTodo(Todo todo) {
        todoList.add(todo);
    }

    public static List<Todo> getAll() {
        return todoList;
    }

    public static Todo generateTodo() {
        return new Todo(generateString(4, 8) + " " + generateString(4, 8) + " " + generateString(4, 8) + " "
                + generateString(4, 8));
    }
}
