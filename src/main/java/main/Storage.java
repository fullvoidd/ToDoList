package main;

import main.model.Todo;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {

    private static int currentId = 1;
    private static ConcurrentHashMap<Integer, Todo> todos = new ConcurrentHashMap<>();

    public static Map<Integer, Todo> getTodoList() {
        return todos;
    }

    public static int addTodo(Todo todo) {
        int id = currentId++;
        todo.setId(id);
        todos.put(id, todo);
        return id;
    }

    public static void putTodoList(ConcurrentHashMap<Integer, Todo> todoList) {
        todos = todoList;
    }

    public static void deleteTodoList() {
        todos.clear();
    }

    public static Todo getTodo(int id) {
        if (todos.containsKey(id)) {
            return todos.get(id);
        }
        return null;
    }

    public static void putTodo(Todo todo, int id) {
        todos.put(id, todo);
    }

    public static void deleteTodo(int id) {
        todos.remove(id);
    }

    public static Todo getTodoOnDate(Date date) {
        for (Integer id : todos.keySet()) {
            if (todos.get(id).getDate().equals(date)) {
                return todos.get(id);
            }
        }
        return null;
    }

    public static void putTodoOnDate(Todo todo, Date date) {
        for (Integer id : todos.keySet()) {
            if(todos.get(id).getDate().equals(date)) {
                todos.replace(id, todo);
            }
        }
    }

    public static void deleteTodoOnDate(Date date) {
        for (Integer id : todos.keySet()) {
            if(todos.get(id).getDate().equals(date)) {
                todos.remove(id);
            }
        }
    }
}
