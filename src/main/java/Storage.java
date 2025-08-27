import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Storage {
    File data;

    public Storage() {
        this.data = new File("./data/Lucid.txt");
        if (!this.data.exists()) {
            try {
                if (!this.data.getParentFile().exists()) {
                    System.out.println("directory does not exist, creating...");
                    this.data.getParentFile().mkdirs();
                }
                this.data.createNewFile();
            } catch (IOException e) {
                System.out.println("error making file");
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
}
