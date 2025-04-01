package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import todo.validator.TaskValidator;

public class TaskService {

    public static void setAsCompleted(int taskId) throws InvalidEntityException {
        Entity entity = Database.get(taskId);

        if (entity instanceof Task) {
            Task task = (Task) entity;
            task.status = Task.Status.Completed;
            Database.update(task);
        } else
            throw new EntityNotFoundException("Can not find the task in database.");
    }

    public static void setAsInProgress(int taskId) throws InvalidEntityException {
        Entity entity = Database.get(taskId);

        if (entity instanceof Task) {
            Task task = (Task) entity;
            task.status = Task.Status.InProgress;
            Database.update(task);
        } else
            throw new EntityNotFoundException("Can not find the task in database.");
    }

    public static void addTask(Task task) throws InvalidEntityException {
        TaskValidator taskValidator = new TaskValidator();
        taskValidator.validate(task);
        task.status = Task.Status.NotStarted;
        Database.add(task);
    }

    public static void updateTask(Task task) throws InvalidEntityException {
        Database.update(task);
    }

    public static void deleteTask(Task task) {
        Database.delete(task.id);
    }
}