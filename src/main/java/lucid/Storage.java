package lucid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * Class to handle the saving and updating of data when changes are made to the task list
 */
public class Storage {
    private File data;

    /**
     * Constructor to initialize the storage, creates a new data folder for data file if it does not already exist
     */
    public Storage() {
        this.data = new File("./data/Lucid.txt");
        if (!this.data.exists()) {
            try {
                if (!this.data.getParentFile().exists()) {
                    Ui.firstTimeUserMessage();
                    boolean successfulDirectoryCreation = this.data.getParentFile().mkdirs();
                    assert successfulDirectoryCreation : "parent directory should be created";
                }
                boolean successfulFileCreation = this.data.createNewFile();
                assert successfulFileCreation : "data file should be created";
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }

    }

    /**
     * Appends task to data file after conversion to correct format
     * @param t Task to append to data file
     */
    public void appendTaskData(Task t) {
        try {
            FileWriter writer;
            writer = new FileWriter(this.data, true);
            writer.append(t.toDataString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error creating FileWriter / writing to file, IOException: " + e.getMessage());
        }
    }

    /**
     * Deletes a task from the data file
     * @param index Position of task in list or row of data file to delete
     */
    public void deleteTaskData(int index) {
        File tempFile = new File("tempFile.txt");
        assert tempFile.exists() : "cannot write to null tempFile";
        try {
            FileWriter tempFileWriter = new FileWriter(tempFile, true);
            BufferedReader reader = new BufferedReader(new FileReader(this.data));
            int row = 1;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (row == index) {
                    row++;
                    continue;
                }
                tempFileWriter.append(currentLine + "\n");
                row++;
            }
            tempFileWriter.close();
            reader.close();

            Files.deleteIfExists(this.data.toPath());
            Files.move(tempFile.toPath(), this.data.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error creating FileWriter / editing to file, IOException: " + e.getMessage());
        }
    }

    /**
     * Edits data file to mark task as complete
     * @param index Position of task in list or row of data file to mark as complete
     */
    public void markTaskDataComplete(int index) {
        File tempFile = new File("tempFile.txt");
        try {
            FileWriter tempFileWriter = new FileWriter(tempFile, true);
            BufferedReader reader = new BufferedReader(new FileReader(this.data));
            int row = 1;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (row == index) {
                    currentLine = currentLine.replaceFirst("NOT DONE", "DONE");
                }
                tempFileWriter.append(currentLine + "\n");
                row++;
            }
            tempFileWriter.close();
            reader.close();

            Files.deleteIfExists(this.data.toPath());
            Files.move(tempFile.toPath(), this.data.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error creating FileWriter / editing to file, IOException: " + e.getMessage());
        }
    }

    /**
     * Edits data file to mark task as uncomplete
     * @param index Position of task in list or row of data file to uncomplete
     */
    public void markTaskDataNotComplete(int index) {
        File tempFile = new File("tempFile.txt");
        try {
            FileWriter tempFileWriter = new FileWriter(tempFile, true);
            BufferedReader reader = new BufferedReader(new FileReader(this.data));
            int row = 1;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (row == index) {
                    currentLine = currentLine.replaceFirst("DONE", "NOT DONE");
                }
                tempFileWriter.append(currentLine + "\n");
                row++;
            }
            tempFileWriter.close();
            reader.close();

            Files.deleteIfExists(this.data.toPath());
            Files.move(tempFile.toPath(), this.data.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error creating FileWriter / editing to file, IOException: " + e.getMessage());
        }
    }

    /**
     * Returns ArrayList containing tasks based on data file
     * @return ArrayList of existing tasks
     */
    // Refactored using ChatGPT to improve SLAP
    public ArrayList<Task> readData() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.data))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                Task task = parseLineToTask(currentLine);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file, IOException: " + e.getMessage());
        } catch (LucidException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    /**
     * Checks type of task line is representing, and calls appropriate function to convert line to that task
     * @param line Line representing task from data file
     * @return Task represented by line
     */
    // Extracted from readData() using ChatGPT to improve SLAP
    private Task parseLineToTask(String line) throws IncorrectDataFormatException {
        String[] args = line.split("\\|");
        String type = args[0].trim();

        switch (type) {
        case "T":
            return parseTodo(args, line);
        case "D":
            return parseDeadline(args, line);
        case "E":
            return parseEvent(args, line);
        default:
            Ui.readDataErrorMessage();
            return null;
        }
    }

    /**
     * Checks correct number of arguments for task type, then calls function to convert line to Todo
     * @param args Array obtained by splitting line using '|'
     * @param line Line representing task from data file
     * @return Todo represented by line
     */
    // Extracted from readData() using ChatGPT to improve SLAP
    private Task parseTodo(String[] args, String line) throws IncorrectDataFormatException {
        if (args.length != 3) {
            Ui.readDataErrorMessage();
            return null;
        }
        return lineToTodo(line);
    }

    /**
     * Checks correct number of arguments for task type, then calls function to convert line to Deadline
     * @param args Array obtained by splitting line using '|'
     * @param line Line representing task from data file
     * @return Deadline represented by line
     */
    // Extracted from readData() using ChatGPT to improve SLAP
    private Task parseDeadline(String[] args, String line) throws IncorrectDataFormatException {
        if (args.length != 4) {
            Ui.readDataErrorMessage();
            return null;
        }
        return lineToDeadline(line);
    }

    /**
     * Checks correct number of arguments for task type, then calls function to convert line to Event
     * @param args Array obtained by splitting line using '|'
     * @param line Line representing task from data file
     * @return Event represented by line
     */
    // Extracted from readData() using ChatGPT to improve SLAP
    private Task parseEvent(String[] args, String line) throws IncorrectDataFormatException {
        if (args.length != 5) {
            Ui.readDataErrorMessage();
            return null;
        }
        return lineToEvent(line);
    }

    /**
     * Converts line from data file to ToDo object
     * @param line Line from data file
     * @return Todo object
     */
    public ToDo lineToTodo(String line) throws IncorrectDataFormatException {
        String[] args = line.split("\\|");
        if (args.length != 3) {
            throw new IncorrectDataFormatException();
        }
        assert args.length == 3 : "todo data must have 3 parts";
        String taskName = args[2].trim();

        ToDo todo = new ToDo(taskName);
        if (args[1].trim().equals("DONE")) {
            todo.markAsComplete();
        }
        return todo;
    }
    /**
     * Converts line from data file to Deadline object
     * @param line Line from data file
     * @return Deadline object
     */
    public Deadline lineToDeadline(String line) throws IncorrectDataFormatException {
        String[] args = line.split("\\|");
        if (args.length != 4) {
            throw new IncorrectDataFormatException();
        }
        assert args.length == 4 : "deadline data must have 4 parts";

        String taskName = args[2].trim();
        String due = args[3].trim();

        Deadline deadline;
        try {
            String[] dateTime = Parser.parseDateTimeString(due);
            if (dateTime[1] == null) {
                deadline = new Deadline(taskName, dateTime[0]);
            } else {
                deadline = new Deadline(taskName, dateTime[0], dateTime[1]);
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }

        if (args[1].trim().equals("DONE")) {
            deadline.markAsComplete();
        }
        return deadline;
    }
    /**
     * Converts line from data file to Event object
     * @param line Line from data file
     * @return Event object
     */
    public Event lineToEvent(String line) throws IncorrectDataFormatException {
        String[] args = line.split("\\|");
        if (args.length != 5) {
            throw new IncorrectDataFormatException();
        }
        assert args.length == 5 : "event data must have 5 parts";

        String taskName = args[2].trim();
        String start = args[3].trim();
        String end = args[4].trim();

        Event event = new Event(taskName, start, end);
        if (args[1].trim().equals("DONE")) {
            event.markAsComplete();
        }
        return event;
    }
}
