package todo.validator;

import java.util.Objects;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step))
            throw new IllegalArgumentException("Type of the entity must be step !!!");

        boolean notMatch = false;
        for (Entity entity2 : Database.entities)
            if (entity2.id == entity.id) {
                notMatch = true;
                break;
            }
        if (notMatch)
            throw new InvalidEntityException("There no match Id for task reference");

        Step step = (Step) entity;

        if (step.title.isEmpty() || Objects.isNull(step))
            throw new InvalidEntityException("Title must be a valid string !!!");
    }
}