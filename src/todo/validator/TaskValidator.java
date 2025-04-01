package todo.validator;

import java.util.Objects;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Task))
            throw new IllegalArgumentException("Type of the entity must be task !!!");

        Task task = (Task) entity;

        if (task.title.isEmpty() || Objects.isNull(task))
            throw new InvalidEntityException("Title must be a valid string !!!");
    }
}