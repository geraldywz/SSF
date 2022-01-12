package ssf.todo.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ssf.todo.model.Todo;
import ssf.todo.util.Todoist;

@Service
public class TodoRepo {
    private final Logger logger = Logger.getLogger(TodoRepo.class.getName());
    private static final String TODO_ENTITY = "TodoList";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void save(final Todo todo) {
        // redisTemplate.opsForList().leftPush(TodoList.generateKey(), todo.getTask());
        redisTemplate.opsForValue().set(TODO_ENTITY + Todoist.generateKey(), todo.getTask(), Duration.ofMinutes(5));
    }

    public Todo findById(final String contactId) {
        Todo result = (Todo) redisTemplate.opsForHash()
                .get(TODO_ENTITY + "_Map", contactId);
        return result;
    }

    public Optional<List<Todo>> getTodoList() {
        List<Object> readFromDB = redisTemplate.opsForList()
                .range(TODO_ENTITY, 0, -1);
        List<Todo> todoList = new ArrayList<>();
        for (Object task : readFromDB) {
            todoList.add(new Todo((String) task));
            logger.info("Task >>>>> " + task);
        }
        return Optional.of(todoList);
    }
}