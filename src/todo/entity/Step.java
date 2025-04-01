package todo.entity;

import db.Entity;

public class Step extends Entity {

    public String title;
    public Status status;
    public int taskRef;
    public static final int Step_ENTITY_CODE = 16;

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
}