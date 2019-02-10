package com.microsoft.springframework.samples.model;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
public class Subtask {

    private String id;
    private String description;
    private boolean finished;
    private String todoItemID;

    public String getTodoItemID() {
        return todoItemID;
    }

    public void setTodoItemID(String todoItemID) {
        this.todoItemID = todoItemID;
    }

    public Subtask() {
    }

    public Subtask(String id, String description, String owner, String todoItemID, boolean isFinished) {
        this.description = description;
        this.id = id;
        this.todoItemID = todoItemID;
        this.finished = isFinished;
    }

    public Subtask(String id, String description, String owner, String todoItemID) {
        new Subtask(id, description, owner, todoItemID,false);
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinish(boolean finished) {
        this.finished = finished;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Subtask)) {
            return false;
        }
        final Subtask task = (Subtask) o;
        return Objects.equals(this.getDescription(), task.getDescription())
                && Objects.equals(this.getID(), task.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id);
    }

    @Override
    public String toString() {
        if (id != null)
            return id + ": " + description;
        else return description;
    }
}
