package fxml;

import alerts.Toast;
import enums.MeasureType;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import misc.DBManager;
import misc.IconManager;
import misc.Methods;
import misc.Session;
import tables.Exercise;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.IOException;

public class CreateExerciseController {

    @FXML private AnchorPane primaryPane;
    @FXML private Button selectIcon;
    @FXML private Button create;
    @FXML private Button cancel;
    @FXML private Tooltip selectIconTooltip;
    @FXML private Tooltip createTooltip;
    @FXML private Tooltip cancelTooltip;
    @FXML private TabPane tabPane;

    @FXML private TextField exerciseName;

    @FXML private TextField timeMinutes;
    @FXML private TextField timeSeconds;
    @FXML private TextField timeIncrease;

    @FXML private TextField unitName;
    @FXML private TextField unitStartValue;
    @FXML private TextField unitIncrease;


    private static final int TIME_MEASURE = 0;
    private static final int UNIT_MEASURE = 1;

    private IconManager icom;
    public CreateExerciseController() {
        icom = IconManager.getInstance();
    }

    private Exercise exercise;

    @FXML
    private void initialize() {
        this.exercise = new Exercise();
        this.exercise.setIconKey("picture");
        //Selection menu
        selectIcon.setGraphic(icom.getIcon(this.exercise.getIconKey()));
        create.setGraphic(icom.getIcon("checkCircle"));
        cancel.setGraphic(icom.getIcon("timesCircle"));
        selectIcon.setTooltip(selectIconTooltip);
        create.setTooltip(createTooltip);
        cancel.setTooltip(cancelTooltip);
    }


    @FXML
    private void checkInput(Event e){
        String exerciseName = this.exerciseName.getText();
        if (exerciseName.equals("")) {
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "No exercise name entered...", 2000, 200, 200);
            return;
        }
        if (tabPane.getSelectionModel().getSelectedIndex() == TIME_MEASURE) {
            checkTimeInput(exerciseName);
        } else if (tabPane.getSelectionModel().getSelectedIndex() == UNIT_MEASURE){
            checkUnitInput(exerciseName);
        } else {
            System.err.println("Selected tab index does not exist");
        }
    }

    private void checkTimeInput(String exerciseName){
        String timeMinutesStr = this.timeMinutes.getText();
        String timeSecondsStr = this.timeSeconds.getText();
        String timeIncreaseStr = this.timeIncrease.getText();
        int timeMinutes;
        int timeSeconds;
        int timeIncrease;

        try {
            timeMinutes = Integer.parseInt(timeMinutesStr.trim());
            if (timeMinutes < 0) {
                Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Minutes must be a number of 0 or higher!", 2000, 200, 200);
                return;
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Minutes must be a number of 0 or higher!", 2000, 200, 200);
            return;
        }
        try {
            timeSeconds = Integer.parseInt(timeSecondsStr.trim());
            if (timeSeconds < 0) {
                Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Seconds cannot be lower than 0!", 2000, 200, 200);
                return;
            }
            if (timeSeconds == 0 && timeMinutes==0) {
                Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Minutes are 0. Seconds must be a number of 1 or higher!", 2000, 200, 200);
                return;
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Seconds must be a number of 1 or higher!", 2000, 200, 200);
            return;
        }
        try {
            timeIncrease = Integer.parseInt(timeIncreaseStr.trim());
            if (timeIncrease < 0) {
                Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Time increase must be a number of 0 or higher!", 2000, 200, 200);
                return;
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Time increase must be a number of 0 or higher!", 2000, 200, 200);
            return;
        }
        long startValue = timeMinutes*60 + timeSeconds;
        createExercise(exerciseName, timeIncrease, MeasureType.TIME, startValue, null);
    }

    private void checkUnitInput(String exerciseName) {
        String unitName = this.unitName.getText();
        String unitStartValueStr = this.unitStartValue.getText();
        String unitIncreaseStr = this.unitIncrease.getText();
        int unitStartValue;
        int unitIncrease;

        if (unitName.equals("")){
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "No unit name entered!", 2000, 200, 200);
            return;
        }
        try {
            unitStartValue = Integer.parseInt(unitStartValueStr.trim());
            if (unitStartValue < 1) {
                Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Start value must be a number of 1 or higher!", 2000, 200, 200);
                return;
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Start value must be a number of 1 and higher!", 2000, 200, 200);
            return;
        }
        try {
            unitIncrease = Integer.parseInt(unitIncreaseStr.trim());
            if (unitIncrease < 1) {
                Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Unit increase value must be a number of 1 or higher!", 2000, 200, 200);
                return;
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Unit increase value must be a number of 1 and higher!", 2000, 200, 200);
            return;
        }
        createExercise(exerciseName, unitIncrease, MeasureType.COUNT, unitStartValue, unitName);
    }

    private void createExercise(String exerciseName, long incrementAmount, MeasureType measureType, long startValue, String unitName){
        this.exercise.setExerciseName(exerciseName);
        this.exercise.setIncrementAmount(incrementAmount);
        this.exercise.setMeasure(measureType);
        this.exercise.setStartValue(startValue);
        if (unitName != null) this.exercise.setUnitName(unitName);
        try {
            EntityManager em = DBManager.getInstance().getEntityManger();
            EntityTransaction utx = em.getTransaction();
            utx.begin();
            em.persist(this.exercise);
            utx.commit();
        } catch (Exception e){
            primaryPane.requestFocus();
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Exercise creation failed...", 2000, 200, 200);
            return;
        }
        Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Exercise  successfully created!", 2000, 200, 200);
        primaryPane.requestFocus();
        Session.getInstance().refreshExercises();
        cancel(null);
    }

    @FXML
    private void selectIcon(){
        Stage stage = new Stage();
        stage.setHeight(800);
        stage.setWidth(1280);
        stage.setResizable(false);
        stage.setTitle("BubbleWorkout");
        stage.getIcons().add(new Image("/include/weight.png"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("selectIcon.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.err.println("Exception when opening page: " + ex);
        }
        if (root != null) {
            SelectIconController l = loader.getController();
            l.setData(this.exercise, this.selectIcon);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryPane.getScene().getWindow());
            stage.show();
        }
    }

    @FXML
    private void cancel(Event e){
        Methods.openPage(e, primaryPane, new FXMLLoader(getClass().getResource("overview.fxml")));
    }


}
