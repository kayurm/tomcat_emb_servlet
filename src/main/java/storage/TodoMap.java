package storage;

import model.TodoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoMap {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<Integer, TodoDTO> todoMap;
    private Integer keyId;

    public TodoMap() {
        todoMap = new HashMap<>();
        keyId = 0;
    }

    public List<TodoDTO> getAllTodos() {
        logger.info("getting all todos");
        return new ArrayList<>(todoMap.values());
    }

    public TodoDTO insertTodo(TodoDTO todo) {
        Integer id = todo.getId();
        if (id == null) {
            logger.info("insertTodo - provided id is null");
            Integer assignedId = ++keyId;
            todo.setId(assignedId);
            todoMap.put(assignedId, todo);
            return todoMap.get(assignedId);
        } else if (isIdExistent(id)) {
            logger.info("insertTodo - provided id exists, updating todo");
            todoMap.replace(id, todo);
            return todoMap.get(id);
        } else {
            logger.info("insertTodo - id was provided but it has no related todo");
            //not in spec
            return null;
        }
    }

    public void deleteAllTodos() {
        logger.info("deleting all todos");
        todoMap.clear();
        keyId = 0;
    }

    public void deleteTodo(Integer id) {
        logger.info("deleting todo {}", id);
        todoMap.remove(id);
    }

    private boolean isIdExistent(Integer id) {
        return todoMap.containsKey(id);
    }
}
