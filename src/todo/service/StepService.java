package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepService {

    public static void saveStep(int taskRef, String title) throws InvalidEntityException {
        Step step = new Step(title, taskRef);
        Database.add(step);
    }

    public static void deleteStep(Step step) {
        Database.delete(step.id);
    }

    public static void setAsCompleted(int stepId) throws InvalidEntityException {
        Entity entity = Database.get(stepId);

        if (entity instanceof Step) {
            Step step = (Step) entity;
            step.status = Step.Status.Completed;
            Database.update(step);
        } else
            throw new EntityNotFoundException("Can not find the step in database.");
    }
}