package todo.entity;

import java.util.Date;

import db.Entity;
import db.Trackable;

public class Step extends Entity implements Trackable{

    public String title;
    public Status status;
    public int taskRef;
    public static final int Step_ENTITY_CODE = 17;

    public enum Status {
        NotStarted, Completed;
    }

    public Step(String title, int taskRef) {
        this.title = title;
        this.taskRef = taskRef;
        this.status = Status.NotStarted;
    }

    @Override
    public Entity copy() {
        Step stepCopy = new Step(this.title, this.taskRef);
        stepCopy.id = id;
        stepCopy.status = status;
        stepCopy.creationDate = creationDate;
        stepCopy.lastModificationDate = lastModificationDate;

        return stepCopy;
    }

    @Override
    public int getEntityCode() {
        return Step_ENTITY_CODE;
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
}