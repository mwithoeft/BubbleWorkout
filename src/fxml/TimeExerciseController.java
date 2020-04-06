package fxml;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import misc.*;
import tables.Entry;
import tables.Exercise;


public class TimeExerciseController {

    @FXML private AnchorPane primaryPane;
    @FXML private AnchorPane timerPane;
    @FXML private Button cancel;
    @FXML private Tooltip cancelTooltip;
    @FXML private Button exerciseIcon;
    @FXML private Label exerciseName;
    @FXML private Button startTimerButton;
    @FXML private Button pauseTimerButton;
    @FXML private Button resetTimerButton;
    @FXML private Tooltip startTimerButtonTooltip;
    @FXML private Tooltip pauseTimerButtonTooltip;
    @FXML private Tooltip resetTimerButtonTooltip;
    @FXML private Label lastTime;
    @FXML private Label nextTime;

    private CountdownTimer timer;
    private long workoutUnits;

    private Exercise exercise;
    private IconManager icom;
    public TimeExerciseController(){
        icom = IconManager.getInstance();
        timer = new CountdownTimer();
    }

    @FXML
    private void initialize(){
        this.cancel.setTooltip(cancelTooltip);
        this.cancel.setGraphic(icom.getIcon("timesCircle"));
        this.startTimerButton.setTooltip(startTimerButtonTooltip);
        this.startTimerButton.setGraphic(icom.getIcon("play"));
        this.pauseTimerButton.setTooltip(pauseTimerButtonTooltip);
        this.pauseTimerButton.setGraphic(icom.getIcon("pause"));
        this.resetTimerButton.setTooltip(resetTimerButtonTooltip);
        this.resetTimerButton.setGraphic(icom.getIcon("stop"));
        this.exerciseName.setMaxWidth(Double.MAX_VALUE);
        this.exerciseName.setAlignment(Pos.BOTTOM_CENTER);
        this.exerciseName.setTextFill(Color.WHITE);
        this.exerciseName.setWrapText(true);
        this.exerciseName.setFont(new Font("Courier New Bold", 40));
        this.timer.setMaxSize(859, 222);
        this.timer.setMinSize(859, 222);
        this.timer.setPrefSize(859, 222);
        this.timer.setMaxWidth(Double.MAX_VALUE);
        this.timer.setAlignment(Pos.BOTTOM_CENTER);
        this.timer.setTextFill(Color.WHITE);
        this.timer.setWrapText(true);
        this.timer.setFont(new Font("Courier New Bold", 150));
        this.timer.setLayoutX(40);
        this.timer.setLayoutY(207);
        this.timer.setFinishedListener(finishedTimer);
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
            this.lastTime.setText("Last time: " + latestEntry.getWorkoutUnits() + " seconds");
        }
        this.nextTime.setText("Next time: " + (this.workoutUnits+this.exercise.getIncrementAmount()) + " seconds");
        timer.init(this.workoutUnits);
        timerPane.getChildren().add(timer);
    }

    @FXML
    private void play(Event e){
        timer.play();
    }
    @FXML
    private void pause(Event e){
        timer.pause();
    }
    @FXML
    private void reset(Event e){
        timer.reset();
    }

    EventHandler<ActionEvent> finishedTimer = e -> {
        DBManager.getInstance().saveEntry(this.exercise, this.workoutUnits, this.primaryPane);
        overview(null);
    };


}
