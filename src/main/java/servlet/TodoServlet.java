package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.TodoDTO;
import storage.TodoMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "TodoServlet",
        urlPatterns = {"/todo"}
)
public class TodoServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    private final TodoMap todoMap = new TodoMap();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        objectMapper.writeValue(resp.getOutputStream(), todoMap.getAllTodos());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TodoDTO reqTodo = objectMapper.readValue(req.getReader(), TodoDTO.class);
        TodoDTO respTodo = todoMap.saveTodo(reqTodo);
        objectMapper.writeValue(resp.getOutputStream(), respTodo);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            todoMap.deleteAllTodos();
        } else {
            todoMap.deleteTodo(Integer.valueOf(id));
        }
        resp.setStatus(200);
        objectMapper.writeValue(resp.getOutputStream(), "");
    }
}
