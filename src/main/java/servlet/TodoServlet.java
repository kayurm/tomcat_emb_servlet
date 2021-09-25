package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import config.TodoConfig;
import model.TodoDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
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

    private ObjectMapper objectMapper;
    private TodoMap todoMap;
    private GenericApplicationContext context;

    @Override
    public void init() {
        context = new AnnotationConfigApplicationContext(TodoConfig.class);
        todoMap = context.getBean(TodoMap.class);
        objectMapper = context.getBean(ObjectMapper.class).enable(SerializationFeature.INDENT_OUTPUT);
    }

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

    @Override
    public void destroy() {
        context.close();
    }
}
