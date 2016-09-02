package com.ankitgaurav.notes;

/**
 * Created by Ankit Gaurav on 24-08-2016.
 */
public class Todo {
    private int _id;
    private String todoText;
    private String createdAt;
    private int isLocked;
    private String is_complete;

    public Todo(){

    }

    public Todo(int _id, int isLocked, String createdAt, String todoText) {
        this._id = _id;
        this.isLocked = isLocked;
        this.createdAt = createdAt;
        this.todoText = todoText;
    }

    public Todo(String todoText) {
        this.todoText = todoText;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    public String getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(String is_complete) {
        this.is_complete = is_complete;
    }
}
