package com;

import com.controllers.TodoController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Todo;
import com.repository.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TodoController.class, secure = false)
public class controllerTestClass {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoRepository todoRepository;
    private long dateMock = new Date().getTime();
    private Todo mockTodo = new Todo(1, "Make toast", true, dateMock);
    private List<Todo> mockTodoList = new ArrayList<>();

    /**
     * Unit test to validate that the end point
     * /api/todos/{id} is returning a single object in the response body
     * that matches the object returned from the repository mock.
     **/
    @Test
    public void retrieveTodo() throws Exception {
        Mockito.when(todoRepository.findById(Mockito.anyInt()))
                .thenReturn(mockTodo);

        this.mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1,'title': 'Make toast','completed': true,'date':" + dateMock + "}"));
    }

    /**
     * Unit test to validate that the end point
     * /api/todos is returning a list of objects in the response body
     * that matches the list returned from the repository mock.
     **/
    @Test
    public void retrieveAllTodos() throws Exception {
        mockTodoList.add(new Todo(1, "Make toast", true, dateMock));
        mockTodoList.add(new Todo(2, "Water garden", false, dateMock));
        Mockito.when(todoRepository.findAll())
                .thenReturn(mockTodoList);

        this.mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id': 1,'title': 'Make toast','completed': true,'date':" + dateMock + "}," +
                        "{'id': 2,'title': 'Water garden','completed': false,'date':" + dateMock + "}]"));
    }

    /**
     * Unit test, validating the todos endpoint with the PUT verb
     **/
    @Test
    public void saveTodo() throws Exception {
        Todo todo = new Todo(0, "Watch game of thrones", false, new Date().getTime());
        doReturn(0).when(todoRepository).save(todo);
        this.mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(todo)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        verify(todoRepository, times(1)).save(refEq(todo));
        verifyNoMoreInteractions(todoRepository);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
