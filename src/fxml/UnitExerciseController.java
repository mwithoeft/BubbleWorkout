package fxml;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import misc.DBManager;
import misc.IconManager;
import misc.Methods;
import misc.Session;
import tables.Entry;
import tables.Exercise;


public class UnitExerciseController {

    @FXML private AnchorPane primaryPane;
    @FXML private AnchorPane unitPane;
    @FXML private Button confirm;
    @FXML private Button cancel;
    @FXML private Tooltip confirmTooltip;
    @FXML private Tooltip cancelTooltip;

    @FXML private Button exerciseIcon;
    @FXML private Label exerciseName;
    @FXML private Label lastTime;
    @FXML private Label nextTime;

    private Label unitLabel;
    private long workoutUnits;

    private Exercise exercise;
    private IconManager icom;
    public UnitExerciseController(){
        icom = IconManager.getInstance();
        unitLabel = new Label();
    }

    @FXML
    private void initialize(){
        this.confirm.setTooltip(confirmTooltip);
        this.cancel.setTooltip(cancelTooltip);
        this.confirm.setGraphic(icom.getIcon("checkCircle"));
        this.cancel.setGraphic(icom.getIcon("timesCircle"));
        this.exerciseName.setMaxWidth(Double.MAX_VALUE);
        this.exerciseName.setAlignment(Pos.BOTTOM_CENTER);
        this.exerciseName.setTextFill(Color.WHITE);
        this.exerciseName.setWrapText(true);
        this.exerciseName.setFont(new Font("Courier New Bold", 40));
        this.unitLabel.setMaxSize(859, 222);
        this.unitLabel.setMinSize(859, 222);
        this.unitLabel.setPrefSize(859, 222);
        this.unitLabel.setMaxWidth(Double.MAX_VALUE);
        this.unitLabel.setAlignment(Pos.BOTTOM_CENTER);
        this.unitLabel.setTextFill(Color.WHITE);
        this.unitLabel.setWrapText(true);
        this.unitLabel.setFont(new Font("Courier New Bold", 150));
        this.unitLabel.setLayoutX(40);
        this.unitLabel.setLayoutY(207);
    }

    @FXML
    private void overview(Event e){
        Methods.openPage(e, primaryPane, new FXMLLoader(getClass().getResource("overview.fxml")));
    }

    public void setData(Exercise e) {
        this.exercise = e;
        this.exerciseIcon.setGraphic(icom.getIcon(this.exercise.getIconKey()));
        this.exerciseName.setText(exercise.getExerciseName());

        Entry latestEntry = Session.getInstance().getAthlete().getLatestEntry(this.exercise);

        if (latestEntry == null) {
            this.workoutUnits = this.exercise.getStartValue();
            this.lastTime.setText("Last time: ---");
        } else {
            this.workoutUnits = latestEntry.getWorkoutUnits() + this.exercise.getIncrementAmount();
            this.lastTime.setText("Last time: " + latestEntry.getWorkoutUnits() +" "+ this.exercise.getUnitName());
        }
        this.nextTime.setText("Next time: " + (this.workoutUnits+this.exercise.getIncrementAmount()) +" "+ this.exercise.getUnitName());
        unitLabel.setText(String.valueOf(this.workoutUnits));
        unitPane.getChildren().add(unitLabel);
    }

    @FXML
    private void finishedExercise(Event e){
        DBManager.getInstance().saveEntry(this.exercise, this.workoutUnits, this.primaryPane);
        overview(null);
    }

}
