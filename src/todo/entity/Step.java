package todo.entity;

import db.Entity;

public class Step extends Entity{

    public String title;
    public Status status;
    public int taskRef;
    public static final int Step_ENTITY_CODE = 16;

    public enum Status {
        NotStarted, Completed;
    }

    @Override
    public Entity copy() {
        Step stepCopy = new Step();
        stepCopy.id = id;
        stepCopy.title = title;
        stepCopy.status = status;
        stepCopy.taskRef = taskRef;
        stepCopy.creationDate = creationDate;
        stepCopy.lastModificationDate = lastModificationDate;

        return stepCopy;
    }

    @Override
    public int getEntityCode() {
        return Step_ENTITY_CODE;
    }
}