package com.repository;

import com.models.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class TodoRepository {

    // Our in-memory database
    private static List<Todo> todoList = new ArrayList<>();

    private static int todoCount = 4;

    // Seeding fictitious data for our database
    static {
        todoList.add(new Todo(1, "Make toast", true, new Date().getTime()));
        todoList.add(new Todo(2, "Water the garden", false, new Date().getTime()));
        todoList.add(new Todo(3, "Feed the cat", true, new Date().getTime()));
        todoList.add(new Todo(4, "Open emails", false, new Date().getTime()));
    }

    // Lists all Packages
    public List<Todo> findAll() {
        return todoList;
    }

    // Save/create a new Todo
    public int save(Todo todo) {
        todo.setId(++todoCount);
        todoList.add(todo);
        return todo.getId();
    }

    // This finds a Todo by the id
    public Todo findById(int id) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }

    // We can delete a Todo by id
    public Todo deleteById(int id) {
        Iterator<Todo> iterator = todoList.iterator();
        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.getId() == id) {
                iterator.remove();
                return todo;
            }
        }
        return null;
    }
}
