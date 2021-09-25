package storage;

import model.TodoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TodoMap {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<Integer, TodoDTO> todoMap = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger();

    public List<TodoDTO> getAllTodos() {
        logger.info("getting all todos");
        return new ArrayList<>(todoMap.values());
    }

    public TodoDTO saveTodo(TodoDTO todo) {
        Integer id = todo.getId();
        if (id == null) {
            logger.info("insertTodo - provided id is null");
            Integer assignedId = counter.incrementAndGet();
            todo.setId(assignedId);
            todoMap.put(assignedId, todo);
            return todoMap.get(assignedId);
        }
        if (idExists(id)) {
            logger.info("insertTodo - provided id exists, updating todo");
            todoMap.put(id, todo);
            return todo;
        }
        logger.info("insertTodo - id was provided but it has no related todo");
        //not in spec
        return null;
    }

    public void deleteAllTodos() {
        logger.info("deleting all todos");
        todoMap.clear();
    }

    public void deleteTodo(Integer id) {
        logger.info("deleting todo {}", id);
        todoMap.remove(id);
    }

    private boolean idExists(Integer id) {
        return todoMap.containsKey(id);
    }
}
