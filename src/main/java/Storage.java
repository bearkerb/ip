import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public void appendTask(Task t) {
        FileWriter writer;
        try {
            writer = new FileWriter(this.data, true);
            writer.append(t.toDataString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error creating FileWriter / writing to file, IOException: " + e.getMessage());
        }
    }
}
