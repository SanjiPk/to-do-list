import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.StepService;
import todo.service.TaskService;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class Main {
    static Scanner sca = new Scanner(System.in);

    public static void main(String[] args) {
        // Register validators with unique entity codes
        Database.registerValidator(Task.Task_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.Step_ENTITY_CODE, new StepValidator());

        String command;
        do {
            command = sca.nextLine().trim();
            switch (command) {
                case "add task":
                    handleAddTask();
                    break;
                case "add step":
                    handleAddStep();
                    break;
                case "delete":
                    handleDelete();
                    break;
                case "update task":
                    handleUpdateTask();
                    break;
                case "update step":
                    handleUpdateStep();
                    break;
                case "get task-by-id":
                    handleGetTaskById();
                    break;
                case "get all-tasks":
                    handleGetAllTasks();
                    break;
                case "get incomplete-tasks":
                    handleGetIncompleteTasks();
                    break;
                case "exit":
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Unknown command.");
            }
        } while (!command.equals("exit"));
        sca.close();
    }

    private static void handleAddTask() {
        try {
            System.out.print("Title: ");
            String title = sca.nextLine();
            System.out.print("Description: ");
            String description = sca.nextLine();
            System.out.print("Due date: ");
            Date dueDate = stringToDate(sca.nextLine());

            Task task = new Task(title, description, dueDate);
            TaskService.addTask(task);
            System.out.println("Task saved successfully.");
            System.out.println("ID: " + task.id);
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save task.");
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Cannot save task.");
            System.out.println("Error: Invalid date format or other error.");
        }
    }

    private static void handleAddStep() {
        try {
            System.out.print("TaskID: ");
            int taskId = Integer.parseInt(sca.nextLine());
            System.out.print("Title: ");
            String title = sca.nextLine();

            int stepId = StepService.saveStep(taskId, title);
            Step step = (Step) Database.get(stepId);
            System.out.println("Step saved successfully.");
            System.out.println("ID: " + stepId);
            System.out.println("Creation Date: " + step.creationDate);
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save step.");
            System.out.println("Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot save step.");
            System.out.println("Error: Cannot find task with ID=" + e.getMessage().split("=")[1]);
        } catch (NumberFormatException e) {
            System.out.println("Cannot save step.");
            System.out.println("Error: Invalid TaskID format.");
        }
    }

    private static void handleDelete() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(sca.nextLine());
            deleteEntityAndRelatedSteps(id);
            System.out.println("Entity with ID=" + id + " successfully deleted.");
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot delete entity with ID=" + e.getMessage().split("=")[1] + ".");
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Cannot delete entity.");
            System.out.println("Error: Invalid ID format.");
        }
    }

    private static void handleUpdateTask() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(sca.nextLine());
            System.out.print("Field: ");
            String field = sca.nextLine().toLowerCase();
            System.out.print("New Value: ");
            String newValue = sca.nextLine();

            Task oldTask = (Task) Database.get(id);
            Task newTask = (Task) oldTask.copy();

            switch (field) {
                case "title":
                    newTask.title = newValue;
                    TaskService.updateTask(newTask);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: title");
                    System.out.println("Old Value: " + oldTask.title);
                    System.out.println("New Value: " + newValue);
                    System.out.println("Modification Date: " + newTask.lastModificationDate);
                    break;
                case "description":
                    newTask.description = newValue;
                    TaskService.updateTask(newTask);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: description");
                    System.out.println("Old Value: " + oldTask.description);
                    System.out.println("New Value: " + newValue);
                    System.out.println("Modification Date: " + newTask.lastModificationDate);
                    break;
                case "due date":
                    newTask.dueDate = stringToDate(newValue);
                    TaskService.updateTask(newTask);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: due date");
                    System.out.println("Old Value: " + oldTask.dueDate);
                    System.out.println("New Value: " + newTask.dueDate);
                    System.out.println("Modification Date: " + newTask.lastModificationDate);
                    break;
                case "status":
                    if (newValue.equals("Completed")) {
                        TaskService.setAsCompleted(id);
                        updateStepsToCompleted(id);
                    } else if (newValue.equals("InProgress")) {
                        TaskService.setAsInProgress(id);
                    } else {
                        System.out.println("Invalid status value.");
                        return;
                    }
                    Task updatedTask = (Task) Database.get(id);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: status");
                    System.out.println("Old Value: " + oldTask.status);
                    System.out.println("New Value: " + updatedTask.status);
                    System.out.println("Modification Date: " + updatedTask.lastModificationDate);
                    break;
                default:
                    System.out.println("Invalid field name.");
            }
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot update task with ID=" + e.getMessage().split("=")[1] + ".");
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidEntityException e) {
            System.out.println("Cannot update task.");
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Cannot update task.");
            System.out.println("Error: Invalid input or date format.");
        }
    }

    private static void handleUpdateStep() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(sca.nextLine());
            System.out.print("Field: ");
            String field = sca.nextLine().toLowerCase();
            System.out.print("New Value: ");
            String newValue = sca.nextLine();

            Step oldStep = (Step) Database.get(id);
            Step newStep = (Step) oldStep.copy();

            if (field.equals("title")) {
                newStep.title = newValue;
                Database.update(newStep);
                System.out.println("Successfully updated the step.");
                System.out.println("Field: title");
                System.out.println("Old Value: " + oldStep.title);
                System.out.println("New Value: " + newValue);
                System.out.println("Modification Date: " + newStep.lastModificationDate);
            } else if (field.equals("status") && newValue.equals("Completed")) {
                StepService.setAsCompleted(id);
                Step updatedStep = (Step) Database.get(id);
                updateTaskStatusBasedOnSteps(updatedStep.taskRef);
                System.out.println("Successfully updated the step.");
                System.out.println("Field: status");
                System.out.println("Old Value: " + oldStep.status);
                System.out.println("New Value: " + updatedStep.status);
                System.out.println("Modification Date: " + updatedStep.lastModificationDate);
            } else {
                System.out.println("Invalid field or value.");
            }
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot update step with ID=" + e.getMessage().split("=")[1] + ".");
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidEntityException e) {
            System.out.println("Cannot update step.");
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Cannot update step.");
            System.out.println("Error: Invalid ID format.");
        }
    }

    private static void handleGetTaskById() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(sca.nextLine());
            Task task = (Task) Database.get(id);
            printTaskDetails(task);
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot find task with ID=" + e.getMessage().split("=")[1] + ".");
        } catch (NumberFormatException e) {
            System.out.println("Cannot find task.");
            System.out.println("Error: Invalid ID format.");
        }
    }

    private static void handleGetAllTasks() {
        ArrayList<Entity> tasks = Database.getAll(Task.Task_ENTITY_CODE);
        tasks.sort(Comparator.comparing(t -> ((Task) t).dueDate));
        for (Entity entity : tasks) {
            printTaskDetails((Task) entity);
            System.out.println();
        }
    }

    private static void handleGetIncompleteTasks() {
        ArrayList<Entity> tasks = Database.getAll(Task.Task_ENTITY_CODE);
        tasks.sort(Comparator.comparing(t -> ((Task) t).dueDate));
        for (Entity entity : tasks) {
            Task task = (Task) entity;
            if (task.status != Task.Status.Completed) {
                printTaskDetails(task);
                System.out.println();
            }
        }
    }

    private static void printTaskDetails(Task task) {
        System.out.println("ID: " + task.id);
        System.out.println("Title: " + task.title);
        System.out.println("Due Date: " + task.dueDate);
        System.out.println("Status: " + task.status);
        ArrayList<Entity> steps = Database.getAll(Step.Step_ENTITY_CODE);
        System.out.println("Steps:");
        for (Entity entity : steps) {
            Step step = (Step) entity;
            if (step.taskRef == task.id) {
                System.out.println("    + " + step.title + ":");
                System.out.println("        ID: " + step.id);
                System.out.println("        Status: " + step.status);
            }
        }
    }

    private static void deleteEntityAndRelatedSteps(int id) {
        Entity entity = Database.get(id);
        if (entity instanceof Task) {
            ArrayList<Entity> steps = Database.getAll(Step.Step_ENTITY_CODE);
            for (Entity step : steps) {
                if (((Step) step).taskRef == id) {
                    Database.delete(step.id);
                }
            }
        }
        Database.delete(id);
    }

    private static void updateStepsToCompleted(int taskId) throws InvalidEntityException {
        ArrayList<Entity> steps = Database.getAll(Step.Step_ENTITY_CODE);
        for (Entity entity : steps) {
            Step step = (Step) entity;
            if (step.taskRef == taskId && step.status != Step.Status.Completed) {
                StepService.setAsCompleted(step.id);
            }
        }
    }

    private static void updateTaskStatusBasedOnSteps(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        ArrayList<Entity> steps = Database.getAll(Step.Step_ENTITY_CODE);
        boolean allCompleted = true;
        boolean anyCompleted = false;

        for (Entity entity : steps) {
            Step step = (Step) entity;
            if (step.taskRef == taskId) {
                if (step.status != Step.Status.Completed) {
                    allCompleted = false;
                } else {
                    anyCompleted = true;
                }
            }
        }

        if (allCompleted && task.status != Task.Status.Completed) {
            TaskService.setAsCompleted(taskId);
        } else if (anyCompleted && task.status == Task.Status.NotStarted) {
            TaskService.setAsInProgress(taskId);
        }
    }

    private static Date stringToDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd.");
        }
    }
}