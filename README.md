# Bob

This is a Personal Assistant Chatbot that helps users to keep track of their tasks. It's named after the minion _Bob_. Given below are instructions on how to use it.

## Quick Start

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Ensure you have Java `17` installed in your Computer.  
   **Mac users**: Ensure you have the precise JDK version specified [here](https://se-education.org/guides/tutorials/javaInstallationMac.html). 
2. Download the latest `.jar` file from [here](https://github.com/laitcanard05/ip/releases/tag/v0.1.0).
3. Copy the file to the folder you want to use as the *home folder* for your Bob. 
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar Bob.jar` command to run the application.  
   You should see something like the below as the output:
   ```
   Hello I'm Bob.
   What can I do for you?

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Features
### Viewing tasks in list: `list`
Shows all the tasks in the current task list and their descriptions including the task name, the type of task, and the completion status.  
Format: `list`

### Adding a Todo task: `todo`
Adds a Todo task into the task list.  
Format: `todo TASK_NAME`
+ Adds a Todo task with the specified `TASK_NAME`  

Examples
+ `todo Do laundry` adds a Todo task into the task list with the task name set to be `Do laundry`
+ The completion status is set to not done by default

### Adding a Deadline task: `deadline`
Adds a Deadline task into the task list.  
Format: `deadline TASK_NAME /by DEADLINE`
+ Adds a Deadline task with the specified `TASK_NAME` and `DEADLINE`
+ `DEADLINE` should be specified in either one of these formats: `yyyy-MM-dd HH:mm`, `yyyy-MM-dd`, `HH:mm`.
+ Chatbot processes the deadline and converts it to the corresponding format: `MMM dd yy HH:mm`, `MMM dd yy`, `HH:mm`
+ The completion status is set to not done by default

Examples
+ `deadline Submit CS2113 iP /by 2025-03-14 23:59` adds a Deadline task into the task list and sets the task name and task deadline to be`Submit CS2113 iP` and `MAR 14 2025 23:59` respectively

### Adding an Event task: `Event`
Adds an Event task into the task list.  
Format: `event TASK_NAME /from START /to END`
+ Adds an Event task with the specified `TASK_NAME`, start date/time `START` and end date/time `END`
+ The completion status is set to not done by default

Examples
+ `event meeting /from 2pm /to 4pm` adds an Event task into the task list and sets the task name, start date/time and end date/time to `meeting`, `2pm` and `4pm`

### Marking a task as done: `Mark`
Changes the completion status of a task in the task list from not done to done.  
Format: `mark INDEX`
+ Marks the task at the specified `INDEX` as done
+ `INDEX` refers to the index number shown in the displayed task list when command `list` is given
+ `INDEX` must be a positive integer 1, 2, 3...

Examples
+ `mark 2` marks the 2nd task in the task list as done

### Marking a task as not done: `Unmark`
Changes the completion status of a task in the task list from done to not done.  
Format: `unmark INDEX`
+ Marks the task at the specified `INDEX` as not done
+ `INDEX` refers to the index number shown in the displayed task list when command `list` is given
+ `INDEX` must be a positive integer 1, 2, 3...

Examples
+ `unmark 3` marks the 3rd task in the task list as not done

### Searching for tasks: `find`
Finds tasks whose names contain the given keyword.  
Format: `find KEYWORD`
+ The search is case-insensitive. e.g. `read` will match `Read`
+ `KEYWORD` is taken as one keyword even if there are spaces in between the words
+ Only the name of the task is searched

Examples
+ `find Read` returns `read book`  
  ![Screenshot of Bob's output after inputting `find Read`](img.png)

### Deleting a task: `delete`
Deletes a specified task from the task list.  
Format: `delete INDEX`
+ Deletes the task at the specified `INDEX` from the task list
+ `INDEX` refers to the index number shown in the displayed task list when command `list` is given
+ `INDEX` must be a positive integer 1, 2, 3...

### Exiting the program: `bye`
Exits the program.  
Format: `bye`

### Saving the data
TaskList data are saved in the hard disk automatically after any command that changes the data.   
There is no need to save manually.
