import db.*;
import example.*;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.StepService;
import todo.service.TaskService;
import db.exception.*;

import java.util.Date;
import java.util.Scanner;

public class Main {
    static Scanner sca = new Scanner(System.in);

    public static void main(String[] args) {

        String command = sca.nextLine();
        int id;
        while (command.equals("exit")) {

            switch (command) {
                case "add task":
                    try {
                        id = addTask();
                        System.out.println("Task saved successfully.");
                        System.out.println(String.format("ID : %d", id));
                    } catch (InvalidEntityException e) {
                        System.out.println("Cannot save task.");
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Cannot save task.");
                        System.out.println(e.getMessage());
                    }
                    break;
                case "add step":
                    try {
                        id = addStep();
                        Step temp = (Step) Database.get(id);
                        System.out.println("Step saved successfully.");
                        System.out.println("ID: " + id);
                        System.out.println("Creation time: " + temp.creationDate);
                    } catch (InvalidEntityException e) {
                        System.out.println("Cannot save step.");
                        System.out.println(e.getMessage());
                    }
                    break;
                case "delete":
                    try {
                        System.out.print("ID: ");
                        id = sca.nextInt();
                        Database.delete(id);
                        System.out.println("Entity with ID=" + id + " successfully deleted.");
                    } catch (EntityNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "update task":
                    System.out.print("ID: ");
                    id = sca.nextInt();
                    try {
                        updateTask(id);
                    } catch (InvalidEntityException e) {
                        System.out.println("Cannot update task with ID=" + id + ".");
                        System.out.println(e.getMessage());
                    } catch (EntityNotFoundException e) {
                        System.out.println("Cannot update task with ID=" + id + ".");
                        System.out.println(e.getMessage());
                    }
                    break;
                case "update step":

                    break;
                default:
                    break;
            }

            command = sca.nextLine();
        }
        sca.close();
    }

    public static int addTask() throws InvalidEntityException {
        System.out.print("Title: ");
        String title = sca.nextLine();
        System.out.print("Description: ");
        String description = sca.nextLine();
        System.out.print("Due date: ");
        String date = sca.nextLine();
        Date dueDate = stringToDate(date);

        Task task = new Task(title, description, dueDate);
        Database.add(task);
        return task.id;
    }

    public static int addStep() throws InvalidEntityException {
        System.out.print("task ID: ");
        int taskId = sca.nextInt();
        System.out.print("Title: ");
        String title = sca.nextLine();

        return StepService.saveStep(taskId, title);
    }

    public static void updateTask(int id) throws InvalidEntityException {

        System.out.print("Field: ");
        String field = sca.nextLine();
        System.out.print("New Value");
        String newValue = sca.nextLine();

        Task oldTask = (Task) Database.get(id);

        if (field.toLowerCase().contains("date")) {
            Date date = stringToDate(newValue);
            Task newTask = (Task) oldTask.copy();
            newTask.dueDate = date;
            TaskService.updateTask(newTask);
            System.out.println("Successfully updated the task.");
            System.out.println("Field: " + field);
            System.out.println("Old value: " + newTask.dueDate);
            System.out.println("New value: " + oldTask.dueDate);
        } else if (field.toLowerCase().contains("title")) {
            Task newTask = (Task) oldTask.copy();
            newTask.title = newValue;
            TaskService.updateTask(newTask);
            System.out.println("Successfully updated the task.");
            System.out.println("Field: " + field);
            System.out.println("Old value: " + newTask.title);
            System.out.println("New value: " + oldTask.title);
        } else if (field.toLowerCase().contains("description")) {
            Task newTask = (Task) oldTask.copy();
            newTask.description = newValue;
            TaskService.updateTask(newTask);
            System.out.println("Successfully updated the task.");
            System.out.println("Field: " + field);
            System.out.println("Old value: " + newTask.description);
            System.out.println("New value: " + oldTask.description);
        } else if (field.toLowerCase().contains("status")) {
            Task newTask = (Task) oldTask.copy();
            if (newValue.equals("Completed")) {
                TaskService.setAsCompleted(id);
            } else if (newValue.equals("InProgress")) {
                TaskService.setAsInProgress(id);
            } else {
                System.out.println("The status value is not valid.");
                return;
            }
            System.out.println("Successfully updated the task.");
            System.out.println("Field: " + field);
            System.out.println("Old value: " + newTask.status);
            System.out.println("New value: " + oldTask.status);
        }
    }

    public static void updateStep(int id) throws InvalidEntityException{

    }

    public static Date stringToDate(String date) {
        String[] dateHelper = date.split("-");
        int year = Integer.valueOf(dateHelper[0]);
        int month = Integer.valueOf(dateHelper[1]);
        int day = Integer.valueOf(dateHelper[2]);
        Date currentDate = new Date(year, month, day);

        return currentDate;
    }
}