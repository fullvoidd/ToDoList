package main;

import main.model.Todo;
import main.model.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos/")
    public List<Todo> getList() {
        Iterable<Todo> iterable = todoRepository.findAll();
        ArrayList<Todo> todos = new ArrayList<>();
        for (Todo todo : iterable) {
            todos.add(todo);
        }
        return todos;
    }

    @PostMapping("/todos/")
    public int addTodo(Todo todo) {
        Todo newTodo = todoRepository.save(todo);
        return newTodo.getId();
    }

    @PutMapping("/todos/")
    public void putTodoList(ConcurrentHashMap<Integer, Todo> todoList) {
        for (Integer id : todoList.keySet()) {
            todoRepository.save(todoList.get(id));
        }
    }

    @DeleteMapping("/todos/")
    public void deleteTodoList() {
        todoRepository.deleteAll();
    }

    @GetMapping("/todos/id/{id}")
    public ResponseEntity getTodo(@PathVariable int id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (!todo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(todo.get(), HttpStatus.OK);
    }

    @PutMapping("/todos/id/{id}")
    public void putTodo(Todo todo, @PathVariable int id) {
        todoRepository.deleteById(id);
        todoRepository.save(todo);
    }

    @DeleteMapping("/todos/id/{id}")
    public void deleteTodo(@PathVariable int id) {
        todoRepository.deleteById(id);
    }

    @GetMapping("/todos/date/{date}")
    public ResponseEntity getTodoOnDate(@PathVariable Date date) {
        Todo todo = null;
        for (Todo nextTodo : todoRepository.findAll()) {
            if (nextTodo.getDate().equals(date)) {
                todo = nextTodo;
                break;
            }
        }
        if (todo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(todo, HttpStatus.OK);
    }

    @PutMapping("/todos/date/{date}")
    public void putTodoOnDate(Todo todo, @PathVariable Date date) {
        for (Todo nextTodo : todoRepository.findAll()) {
            if (nextTodo.getDate().equals(date)) {
                todoRepository.deleteById(nextTodo.getId());
                todoRepository.save(todo);
                break;
            }
        }
    }

    @DeleteMapping("/todos/date/{date}")
    public void deleteTodoOnDate(@PathVariable Date date) {
        for (Todo nextTodo : todoRepository.findAll()) {
            if (nextTodo.getDate().equals(date)) {
                todoRepository.deleteById(nextTodo.getId());
                break;
            }
        }
    }
}
