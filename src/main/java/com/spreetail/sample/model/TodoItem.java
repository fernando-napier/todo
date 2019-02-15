/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.spreetail.sample.model;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;


import java.util.Date;
import java.util.Objects;

@Document
public class TodoItem {
    private String id;
    private String title;
    private String description;
    private String owner;
    private boolean finished;
    private Date dueDate;
    private Date completedDate;
    private ProgressType progressType;
    private PriorityType priorityType;

    public TodoItem(String id, String description, String owner, ProgressType type, PriorityType priorityType,
                    Date dueDate, Date completedDate, String title) {
        this.description = description;
        this.id = id;
        this.owner = owner;
        this.finished = false;
        this.progressType = type;
        this.priorityType = priorityType;
        this.dueDate = dueDate;
        this.completedDate = completedDate;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public PriorityType getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(PriorityType priorityType) {
        this.priorityType = priorityType;
    }

    public ProgressType getProgressType() {
        return progressType;
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
    }

    public TodoItem() {
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
        if (!(o instanceof TodoItem)) {
            return false;
        }
        final TodoItem group = (TodoItem) o;
        return Objects.equals(this.getTitle(), group.getTitle())
                && Objects.equals(this.getDescription(), group.getDescription())
                && Objects.equals(this.getOwner(), group.getOwner())
                && Objects.equals(this.getID(), group.getID())
                && Objects.equals(this.getProgressType(), group.getProgressType())
                && Objects.equals(this.getPriorityType(), group.getPriorityType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, owner);
    }

    @Override
    public String toString() {
        if (id != null)
            return id + ": " + description;
        else return description;
    }
}

