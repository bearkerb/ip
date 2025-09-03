package lucid;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Lucid lucid;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image lucidImage = new Image(this.getClass().getResourceAsStream("/images/Lucid.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects lucid instance
     * @param l lucid instance
     */
    public void setLucid(Lucid l) {
        lucid = l;
        DialogBox greeting = DialogBox.getLucidDialog(Ui.introduction(), lucidImage);
        dialogContainer.getChildren().addAll(greeting);
    }

    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();
        String lucidText = lucid.getResponse(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getLucidDialog(lucidText, lucidImage)
        );
        userInput.clear();
    }
}
