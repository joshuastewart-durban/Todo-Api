package com.models;

import java.io.Serializable;

public class Todo implements Serializable {

    private int id;

    private String title;

    private Boolean completed;

    private long date;

    public Todo() {
    }

    public Todo(int id, String title, Boolean completed, long date) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

}
