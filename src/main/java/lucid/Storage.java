package lucid;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Storage {
    File data;

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

    public void completeTaskData(int index) {
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

    public void uncompleteTaskData(int index) {
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

    public ArrayList<Task> readData() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.data));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] args = currentLine.split("\\|");
                String type = args[0].trim();
                switch (type) {
                case ("T") :
                    if (args.length != 3) {
                        Ui.readDataErrorMessage();
                        break;
                    }
                    tasks.add(lineToToDo(currentLine));
                    break;
                case ("D") :
                    if (args.length != 4) {
                        Ui.readDataErrorMessage();
                        break;
                    }
                    tasks.add(lineToDeadline(currentLine));
                    break;
                case ("E") :
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

    public ToDo lineToToDo(String line) {
        String[] args = line.split("\\|");
        String taskName = args[2].trim();
        ToDo todo = new ToDo(taskName);
        if (args[1].trim().equals("DONE")) {
            todo.complete();
        }
        return todo;
    }

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
            deadline.complete();
        }
        return deadline;
    }

    public Event lineToEvent(String line) {
        String[] args = line.split("\\|");
        String taskName = args[2].trim();
        String start = args[3].trim();
        String end = args[4].trim();
        Event event = new Event(taskName, start, end);
        if (args[1].trim().equals("DONE")) {
            event.complete();
        }
        return event;
    }
}
