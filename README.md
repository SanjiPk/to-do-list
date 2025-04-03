# To-Do List Manager

A simple command-line application built in Java to manage tasks and their associated steps. This project uses an in-memory database to store tasks and steps, allowing users to create, update, delete, and retrieve them with a focus on clean architecture and exception handling.
Features

    Task Management: Add, update, delete, and view tasks with titles, descriptions, due dates, and statuses (NotStarted, InProgress, Completed).
    Step Management: Add, update, and delete steps associated with tasks, with statuses (NotStarted, Completed).
    Automatic Status Updates:
        Completing a task marks all its steps as completed.
        Completing all steps of a task marks the task as completed.
        Completing a step of a NotStarted task changes its status to InProgress.
    In-Memory Database: Stores entities with unique IDs, creation dates, and modification dates.
    Command-Line Interface: Interactive console for managing tasks and steps.

# Project Structure
```text
src/
├── db/
│   ├── Database.java           # In-memory database for storing entities
│   ├── Entity.java             # Abstract base class for entities
│   ├── Trackable.java          # Interface for entities with creation/modification dates
│   ├── Validator.java          # Interface for entity validation
│   └── exception/
│       ├── EntityNotFoundException.java  # Exception for missing entities
│       └── InvalidEntityException.java   # Exception for invalid entities
├── todo/
│   ├── entity/
│   │   ├── Step.java           # Step entity with title, status, and task reference
│   │   └── Task.java           # Task entity with title, description, due date, and status
│   ├── service/
│   │   ├── StepService.java    # Service layer for step operations
│   │   └── TaskService.java    # Service layer for task operations
│   └── validator/
│       ├── StepValidator.java  # Validator for Step entities
│       └── TaskValidator.java  # Validator for Task entities
└── Main.java                   # Entry point with command-line interface
```
Prerequisites

    Java Development Kit (JDK): Version 8 or higher.
    IDE or Command Line: Use an IDE like IntelliJ IDEA, Eclipse, or compile/run via terminal.

Setup

1. Clone the Repository:
```bash
git clone https://github.com/your-username/todo-list-manager.git
cd todo-list-manager
```
Replace your-username with your GitHub username and todo-list-manager with your repository name.
2. Compile the Project: Using a terminal:
```bash
javac src/*.java src/db/*.java src/db/exception/*.java src/todo/entity/*.java src/todo/service/*.java src/todo/validator/*.java
```
Or use your IDE’s build tools.
3. Run the Application:
```bash
java -cp src Main
```
# Usage

The application runs in the console and accepts commands until you type exit. Below are the supported commands with examples:
#Add a Task
```text
add task
Title: AP Project
Description: Advanced Programming project
Due date: 2025-04-04
```
Output:
```text
Task saved successfully.
ID: 1
```
# Add a Step
```text
add step
TaskID: 1
Title: Implement database
```
Output
```text
Step saved successfully.
ID: 2
Creation Date: Thu Apr 03 12:34:56 GMT 2025
```
# Update task
```text
Step saved successfully.
ID: 2
Creation Date: Thu Apr 03 12:34:56 GMT 2025
```
Output
```text
Successfully updated the task.
Field: status
Old Value: NotStarted
New Value: Completed
Modification Date: Thu Apr 03 12:35:02 GMT 2025
```
# Get Task by ID
```text
get task-by-id
ID: 1
```
Output
```text
ID: 1
Title: AP Project
Due Date: Fri Apr 04 00:00:00 GMT 2025
Status: Completed
Steps:
    + Implement database:
        ID: 2
        Status: Completed
```
# Get All Tasks
```text
get all-tasks
```
Output
```text
ID: 1
Title: AP Project
Due Date: Fri Apr 04 00:00:00 GMT 2025
Status: Completed
Steps:
    + Implement database:
        ID: 2
        Status: Completed
```
# Delete task
```text
delete
ID: 1
```
Output
```text
Entity with ID=1 successfully deleted.
```
Note: Deleting a task also deletes its associated steps.
# Exit
```text
exit
```
Output
```text
Exiting program.
```

## Commands
|Command	|Description	|Example Input|
|-----------|---------------|-------------|
|add task	|Add a new task	|Title: Test<br>Description: Test<br>Due date: 2025-04-04|
|add step	|Add a step to a task	|TaskID: 1<br>Title: Test Step|
|update task	|Update a task’s field	|ID: 1<br>Field: title<br>New Value: New Title|
|update step	|Update a step’s field	|ID: 2<br>Field: status<br>New Value: Completed|
|delete     |Delete an entity by ID	|ID: 1|
|get task-by-id	|View a task and its steps by ID	|ID: 1|
|get all-tasks	|List all tasks, sorted by due date	|(No input)|
|get incomplete-tasks	|List incomplete tasks	|(No input)|
|exit	|Exit the application	|(No input)|

### Notes
    Date Format: Due dates must be in yyyy-MM-dd (e.g., 2025-04-04).
    Error Handling: The application handles invalid inputs (e.g., wrong IDs, formats) with clear error messages.
    In-Memory Storage: Data is not persisted between runs; it’s stored in memory and lost on exit.
