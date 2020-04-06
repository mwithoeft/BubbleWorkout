package fxml;

import alerts.Toast;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import misc.DBManager;
import misc.Methods;
import misc.Session;
import tables.Athlete;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;

public class LoginController {

    @FXML private AnchorPane primaryPane;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Label passwordShow;

    final BooleanProperty firstTime;

    public LoginController() {
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
        passwordShow.setVisible(false);
    }


    @FXML
    private void keyLogin(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            performLogin(username.getText(), password.getText());
        } else {
            passwordShow.setText(password.getText());
        }
    }
    @FXML
    private void buttonLogin(Event e) {
        performLogin(username.getText(), password.getText());
    }
    private void performLogin(String username, String password) {
        EntityManager em = DBManager.getInstance().getEntityManger();
        Query q = em.createQuery("SELECT a FROM Athlete a WHERE a.username = :username AND a.password = :password");
        q.setParameter("username", username);
        q.setParameter("password", password);
        q.setMaxResults(1);

        Athlete a = null;
        try {
            a = (Athlete) q.getSingleResult();
        } catch (Exception e) {
        }
        if (a == null) {
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Wrong password or username...", 2000, 200, 200);
            return;
        } else {
            a = em.merge(a);
            Session.getInstance().startSession(a);

            overview();
        }
    }

    @FXML
    private void toggleShowPasswordKey(KeyEvent e){
        if (e.getCode() == KeyCode.ENTER) {
            if (checkBox.isSelected()) {
                checkBox.setSelected(false);
            } else {
                checkBox.setSelected(true);
            }
            toggleShowPassword(null);
        }
    }

    @FXML
    private void toggleShowPassword(ActionEvent e) {
        if (checkBox.isSelected()) {
            passwordShow.setVisible(true);
        } else {
            passwordShow.setVisible(false);
        }
    }


    @FXML
    private void signUp(Event e) {
        if (Methods.checkInput(e)) {
            Stage stage = (Stage) primaryPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createAccount.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                System.err.println("Exception when opening sign up page: " + ex);
            }
            if (root != null) {
                Scene scene = new Scene(root);
                stage.setScene(scene);
            }
        }
    }

    private void overview(){
        Methods.openPage(null, primaryPane, new FXMLLoader(getClass().getResource("overview.fxml")));
    }

}
