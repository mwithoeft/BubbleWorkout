package fxml;

import de.jensd.fx.glyphs.GlyphIcons;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import misc.IconManager;
import misc.PropertyButton;
import tables.Exercise;

import java.util.Set;
import java.util.TreeMap;


public class SelectIconController {

    @FXML
    private AnchorPane primaryPane;
    @FXML
    private Button selectedIcon;
    @FXML
    private Tooltip selectedIconTooltip;
    @FXML
    private Button confirm;
    @FXML
    private Tooltip confirmTooltip;
    @FXML
    private Button cancel;
    @FXML
    private Tooltip cancelTooltip;
    @FXML
    private ScrollPane scrollPane;


    private String currentIconKey;
    private Exercise exercise;
    private Button exerciseButton;


    private IconManager icom;
    private static final double BUTTON_PADDING = 15;
    public SelectIconController(){
        icom = IconManager.getInstance();
    }

    @FXML
    private void initialize(){
        this.selectedIcon.setTooltip(selectedIconTooltip);
        this.confirm.setTooltip(confirmTooltip);
        this.cancel.setTooltip(cancelTooltip);
        this.confirm.setGraphic(icom.getIcon("checkCircle"));
        this.cancel.setGraphic(icom.getIcon("timesCircle"));

        fillScrollPane();
    }
    private void fillScrollPane(){
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(BUTTON_PADDING));
        flowPane.setHgap(BUTTON_PADDING);
        flowPane.setVgap(BUTTON_PADDING);
        flowPane.prefWidthProperty().bind(scrollPane.widthProperty());
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        TreeMap<String, GlyphIcons> map = icom.getAll();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            PropertyButton<String> b = new PropertyButton<>(key);
            b.setGraphic(icom.getIcon(key));
            b.setPrefSize(150, 150);
            b.setMinSize(150, 150);
            b.setMaxSize(150, 150);
            b.getStyleClass().add("form_background");
            b.setCursor(Cursor.HAND);
            b.addEventFilter(MouseEvent.MOUSE_CLICKED, iconClickedHandler);
            Tooltip t = new Tooltip("name: " + key);
            b.setTooltip(t);

            flowPane.getChildren().add(b);
        }
        scrollPane.setContent(flowPane);
    }

    EventHandler<MouseEvent> iconClickedHandler = e -> {
        PropertyButton<String> b = (PropertyButton) e.getSource();
        this.currentIconKey = b.getProperty();
        this.selectedIcon.setGraphic(icom.getIcon(this.currentIconKey));
    };

    public void setData(Exercise e, Button b){
        this.exercise = e;
        this.currentIconKey = exercise.getIconKey();
        this.selectedIcon.setGraphic(icom.getIcon(this.currentIconKey));
        this.exerciseButton = b;
    }

    public void confirm(Event e) {
        this.exercise.setIconKey(this.currentIconKey);
        this.exerciseButton.setGraphic(icom.getIcon(this.currentIconKey));

        close(null);
    }

    @FXML
    private void close(Event e){
        primaryPane.getScene().getWindow().hide();
    }
}
