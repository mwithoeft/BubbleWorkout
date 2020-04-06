package alerts;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SuccessAlert extends Alert {

    public SuccessAlert(String message, String header, Stage owner) {
        super(AlertType.INFORMATION);
        this.setContentText(message);
        this.setHeaderText(header);
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("include/weight.png"));
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(owner);
        this.show();
    }

}
