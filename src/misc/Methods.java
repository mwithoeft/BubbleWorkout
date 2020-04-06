package misc;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Methods {


    public static boolean checkInput(Event e) {
        if (e instanceof KeyEvent) {
            KeyEvent s = (KeyEvent) e;
            if (s.getCode() == KeyCode.ENTER) {
                return true;
            }
        } else if (e instanceof MouseEvent) {
            return true;
        } else if (e == null) {
            return true;
        }
        return false;
    }

    public static void openPage(Event e, Pane primaryPane, FXMLLoader loader) {
        if (Methods.checkInput(e)) {
            Stage stage = (Stage) primaryPane.getScene().getWindow();
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                System.err.println("Exception when opening page: " + ex);
            }
            if (root != null) {
                Scene scene = new Scene(root);
                stage.setScene(scene);
            }
        }
    }


}
