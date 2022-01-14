package ssf.todo.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import ssf.todo.model.Task;
import ssf.todo.model.Todo;
import ssf.todo.model.User;

@Service
public class Taskmaster {
    private static List<String> userList = new ArrayList<>();
    private static final String USERNAME_KEY = "User name";
    private static final String TODOLIST_KEY = "Todo list";
    private static final String TASKNAME_KEY = "Task name";
    private static final String COMPLETION_KEY = "Completion";
    private static final Logger logger = LoggerFactory.getLogger(Taskmaster.class);

    public static User getUser(String name) {
        User userFound = null;
        for (String json : userList) {
            User user = JSONToUser(json).get();
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
            User user = JSONToUser(json).get();
            userNames.add(user.getName());
        }
        return userNames;
    }

    public static void saveUser(User user) {
        userList.add(userToJSON(user).get().toString());
    }

    private static Optional<JsonObject> taskToJSON(Task task) {

        return Optional.of(
                Json.createObjectBuilder()
                        .add(TASKNAME_KEY, task.getName())
                        .add(COMPLETION_KEY, task.getCompletion())
                        .build());
    }

    private static Optional<JsonObject> userToJSON(User user) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        user.getTodoList()
                .forEach(task -> {
                    arrayBuilder.add(
                            taskToJSON(task).get());
                });

        return Optional.of(
                Json.createObjectBuilder()
                        .add(USERNAME_KEY, user.getName())
                        .add(TODOLIST_KEY, arrayBuilder.build())
                        .build());
    }

    private static Optional<Task> JSONtoTask(String json) {
        JsonObject taskObject = Json.createReader(
                new StringReader(json))
                .readObject();
        return Optional.of(
                new Task(
                        taskObject.getString(TASKNAME_KEY).toString(),
                        taskObject.getBoolean(COMPLETION_KEY)));
    }

    private static Optional<User> JSONToUser(String json) {
        JsonObject userObject = Json.createReader(
                new StringReader(json))
                .readObject();
        JsonArray jsonArray = userObject.getJsonArray(TODOLIST_KEY);

        List<Task> todoList = new ArrayList<>();
        jsonArray.forEach(task -> {
            todoList.add(JSONtoTask(task.toString()).get());
        });

        return Optional.of(
                new User(
                        userObject.getString(USERNAME_KEY),
                        todoList));
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
                String newUser = userToJSON(
                        new User(
                                users.get(userIndex),
                                tasks)).get()
                                        .toString();
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
