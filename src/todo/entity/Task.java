package todo.entity;

import db.Entity;
import db.Trackable;
import java.util.Date;

public class Task extends Entity implements Trackable {

    public enum Status {
        Completed, InProgress, NotStarted;
    }

    public String title;
    public String description;
    public Date dueDate;
    public Status status;
    public static final int Task_ENTITY_CODE = 16;

    public Task(String title, String description, Date dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = Status.NotStarted;
    }

    @Override
    public void setCreationDate(Date date) {
        creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public Entity copy() {
        Task taskCopy = new Task(title, description, dueDate);
        taskCopy.id = id;
        taskCopy.status = status;
        taskCopy.creationDate = creationDate;
        taskCopy.lastModificationDate = lastModificationDate;
        taskCopy.dueDate = dueDate;

        return taskCopy;
    }

    @Override
    public int getEntityCode() {
        return Task_ENTITY_CODE;
    }
}