package com.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import com.models.Todo;
import com.repository.TodoRepository;

@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @RequestMapping("/")
    public String index() {
        return "Congratulations from BlogController.java";
    }

    @GetMapping("/api/todos")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @GetMapping("/api/todos/{id}")
    public Todo getTodo(@PathVariable int id) {
        return todoRepository.findById(id);
    }

    @PostMapping("/api/todos")
    public ResponseEntity<Void> createTodo(@RequestBody Todo todo) {
        try{
            int id = todoRepository.save(todo);
            return ResponseEntity.created(new URI("/api/todos/"+id)).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
        }
    }

    @DeleteMapping("/api/todos/{id}")
    public Todo deleteTodo(@PathVariable int id) {
        return todoRepository.deleteById(id);
    }
}
