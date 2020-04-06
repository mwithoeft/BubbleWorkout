package fxml;

import alerts.Toast;
import de.jensd.fx.glyphs.GlyphIcons;
import enums.MeasureType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import misc.*;
import tables.Exercise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OverviewController {

    @FXML private AnchorPane primaryPane;

    @FXML private ScrollPane exercisePane;

    @FXML private Button newExercise;
    @FXML private Button stats;
    @FXML private Button changeLogin;
    @FXML private Button logout;
    @FXML private Tooltip newExerciseTooltip;
    @FXML private Tooltip statsTooltip;
    @FXML private Tooltip changeLoginTooltip;
    @FXML private Tooltip logoutTooltip;
    @FXML private ListView exercisesList;


    private static final double BUTTON_PADDING = 15;
    private IconManager icom;
    public OverviewController() {
        icom = IconManager.getInstance();
    }

    @FXML
    private void initialize() {
        //Selection menu
        newExercise.setGraphic(icom.getIcon("addCircle"));
        stats.setGraphic(icom.getIcon("archive"));
        changeLogin.setGraphic(icom.getIcon("gear"));
        logout.setGraphic(icom.getIcon("signOut"));
        newExercise.setTooltip(newExerciseTooltip);
        stats.setTooltip(statsTooltip);
        changeLogin.setTooltip(changeLoginTooltip);
        logout.setTooltip(logoutTooltip);

        buildExercisesList();
        buildExercisesPane();
    }

    private void buildExercisesList(){
        List<Exercise> exercises = Session.getInstance().getExercises();
        List<HBox> list = new ArrayList<>();
        if (exercises.isEmpty()) {
            HBox box = new HBox();
            Label label = new Label();
            label.setText("No exercises available");
            label.setMaxWidth(Double.MAX_VALUE);
            label.setAlignment(Pos.BOTTOM_CENTER);
            label.setTextFill(Color.WHITE);
            label.setWrapText(true);
            label.setFont(new Font("Courier New Bold", 15));
            box.getChildren().addAll(label);
            HBox.setHgrow(label, Priority.ALWAYS);
            box.getStyleClass().add("exercise_list");
            list.add(box);
        } else {
            for (Exercise e : exercises) {
                HBox box = new HBox();
                Label label = new Label();
                label.setText(e.getExerciseName());
                label.setMaxWidth(Double.MAX_VALUE);
                label.setAlignment(Pos.BOTTOM_CENTER);
                label.setTextFill(Color.WHITE);
                label.setWrapText(true);
                label.setFont(new Font("Courier New Bold", 15));
                HBox.setHgrow(label, Priority.ALWAYS);
                PropertyButton<Exercise> button = new PropertyButton<>(e);
                button.setMinSize(15, 15);
                button.setMaxSize(15, 15);
                button.setPrefSize(15, 15);
                box.getChildren().addAll(label, button);
                button.setGraphic(icom.getIcon("plus", "1em"));
                button.setCursor(Cursor.HAND);
                box.getStyleClass().add("exercise_list");
                HBox.setMargin(button, new Insets(1, 10, 0, 0));
                button.addEventFilter(MouseEvent.MOUSE_CLICKED, addExerciseToAthlete);
                list.add(box);
            }
        }
        ObservableList<HBox> myObservableList = FXCollections.observableList(list);
        exercisesList.setItems(myObservableList);
    }

    EventHandler<MouseEvent> addExerciseToAthlete = e -> {
        PropertyButton<Exercise> b = (PropertyButton<Exercise>) e.getSource();
        Session.getInstance().getAthlete().addExercise(b.getProperty());
        try {
            DBManager.getInstance().update(Session.getInstance().getAthlete());
            Session.getInstance().refreshExercises();
            buildExercisesPane();
            buildExercisesList();
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Exercise successfully added...", 2000, 200, 200);
        } catch (Exception ex) {
            primaryPane.requestFocus();
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Exercise could not be added to athlete...", 2000, 200, 200);
            return;
        }
    };

    @FXML
    private void logout(Event e){
        if (Methods.checkInput(e)) {
            Stage stage = (Stage) primaryPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                System.err.println("Exception when opening sign in page: " + ex);
            }
            if (root != null) {
                Scene scene = new Scene(root);
                stage.setScene(scene);
            }
            Session.getInstance().endSession();
        }
    }

    @FXML
    private void createExercise(Event e){
        Methods.openPage(e, primaryPane, new FXMLLoader(getClass().getResource("createExercise.fxml")));
    }

    private void buildExercisesPane(){
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(BUTTON_PADDING));
        flowPane.setHgap(BUTTON_PADDING);
        flowPane.setVgap(BUTTON_PADDING);
        flowPane.prefWidthProperty().bind(exercisePane.widthProperty());
        exercisePane.setFitToWidth(true);
        exercisePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        List<Exercise> exercises = Session.getInstance().getAthlete().getExercises();
        if (exercises.isEmpty()){
            Label label = new Label();
            label.setText("You have no exercises added to your account yet");
            label.setMaxWidth(Double.MAX_VALUE);
            label.setAlignment(Pos.BOTTOM_CENTER);
            label.setTextFill(Color.WHITE);
            label.setWrapText(true);
            label.setFont(new Font("Courier New Bold", 15));
            flowPane.getChildren().add(label);
        } else {
            for (Exercise e : exercises) {
                PropertyButton<Exercise> b = new PropertyButton<>(e);
                b.setGraphic(icom.getIcon(e.getIconKey()));
                b.setPrefSize(150, 150);
                b.setMinSize(150, 150);
                b.setMaxSize(150, 150);
                b.getStyleClass().add("form_background");
                b.setCursor(Cursor.HAND);
                b.addEventFilter(MouseEvent.MOUSE_CLICKED, startExercise);
                Tooltip t = new Tooltip(e.getExerciseName());
                b.setTooltip(t);
                flowPane.getChildren().add(b);
            }
        }
        exercisePane.setContent(flowPane);
    }

    EventHandler<MouseEvent> startExercise = e -> {
        PropertyButton<Exercise> b = (PropertyButton<Exercise>) e.getSource();
        Exercise exercise = b.getProperty();
        FXMLLoader loader;
        if (exercise.getMeasure() == MeasureType.COUNT) {
            loader = new FXMLLoader(getClass().getResource("unitExercise.fxml"));
            Methods.openPage(e, primaryPane, loader);
            UnitExerciseController con = loader.getController();
            con.setData(exercise);
        } else if (exercise.getMeasure() == MeasureType.TIME) {
            loader = new FXMLLoader(getClass().getResource("timeExercise.fxml"));
            Methods.openPage(e, primaryPane, loader);
            TimeExerciseController con = loader.getController();
            con.setData(exercise);
        }
    };
}
