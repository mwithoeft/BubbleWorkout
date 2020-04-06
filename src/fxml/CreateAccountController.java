package fxml;

import alerts.Toast;
import enums.Language;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import misc.DBManager;
import misc.Methods;
import tables.Athlete;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Date;


public class CreateAccountController {

    @FXML
    private AnchorPane primaryPane;

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private TextField conf_password;

    final BooleanProperty firstTime;

    public CreateAccountController() {
        firstTime = new SimpleBooleanProperty(true);
    }

    @FXML
    private void initialize() {
        username.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                primaryPane.requestFocus();
                firstTime.setValue(false);
            }
        });
    }

    @FXML
    private void keySignUp(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            trySignUp(null);
        }
    }
    @FXML
    private void trySignUp(Event e) {
        String username = this.username.getText();
        String email = this.email.getText();
        String password = this.password.getText();
        String conf_password = this.conf_password.getText();

        if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Invalid email!", 2000, 200, 200);
            return;
        }
        if (password.equals("")) {
            primaryPane.requestFocus();
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "No password entered!", 2000, 200, 200);
            return;
        }
        if (!password.equals(conf_password)) {
            primaryPane.requestFocus();
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Passwords do not match!", 2000, 200, 200);
            return;
        }

        performSignUp(username, password, email);

    }

    private void performSignUp(String username, String password, String email){
        Athlete n = new Athlete();
        n.setUsername(username);
        n.setPassword(password);
        n.setEmail(email);
        n.setRegistrationDate(new Date());
        n.setLanguage(Language.ENGLISH);
        try {
            DBManager.getInstance().update(n);
        } catch (Exception e){
            primaryPane.requestFocus();
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Username already taken...", 2000, 200, 200);
            return;
        }
        Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Registration successful... Welcome!", 2000, 200, 200);
        primaryPane.requestFocus();
        cancelSignUp(null);
    }

    @FXML
    private void cancelSignUp(Event e) {
        Methods.openPage(e, primaryPane, new FXMLLoader(getClass().getResource("login.fxml")));
    }

}
