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
        this.data = new File("./data/Lucid.Lucid.txt");
        if (!this.data.exists()) {
            try {
                if (!this.data.getParentFile().exists()) {
                    Ui.firstTimeUserMessage();
                    this.data.getParentFile().mkdirs();
                }
                this.data.createNewFile();
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
    public ArrayList<Task> readData() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.data));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] args = currentLine.split("\\|");
                String type = args[0].trim();
                switch (type) {
                case ("T"):
                    if (args.length != 3) {
                        Ui.readDataErrorMessage();
                        break;
                    }
                    tasks.add(lineToTodo(currentLine));
                    break;
                case ("D"):
                    if (args.length != 4) {
                        Ui.readDataErrorMessage();
                        break;
                    }
                    tasks.add(lineToDeadline(currentLine));
                    break;
                case ("E"):
                    if (args.length != 5) {
                        Ui.readDataErrorMessage();
                        break;
                    }
                    tasks.add(lineToEvent(currentLine));
                    break;
                default:
                    Ui.readDataErrorMessage();
                    break;
                }

            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file, IOException: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Converts line from data file to ToDo object
     * @param line Line from data file
     * @return Todo object
     */
    public ToDo lineToTodo(String line) {
        String[] args = line.split("\\|");
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
    public Deadline lineToDeadline(String line) {
        String[] args = line.split("\\|");
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
    public Event lineToEvent(String line) {
        String[] args = line.split("\\|");
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
