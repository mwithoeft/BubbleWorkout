package misc;

import enums.TimerStatus;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

import static enums.TimerStatus.*;

public class CountdownTimer extends Label {

    private TimerStatus status = PLAY;
    private Long startTime;
    private LongProperty timeSeconds;
    private Timeline timeline;

    public CountdownTimer() {
        timeline = new Timeline();
    }

    public void init(long startTime){
        this.startTime = startTime;
        this.timeSeconds = new SimpleLongProperty(startTime);
        this.textProperty().bind(timeSeconds.asString());
        reset();
    }

    public void play() {
        if (this.status == RESET) {
            this.status = PLAY;
            timeline.playFromStart();
        } else if (this.status == PAUSE) {
            this.status = PLAY;
            timeline.play();
        }
    }

    public void pause(){
        if (this.status == PLAY) {
            this.status = PAUSE;
            timeline.pause();
        }
    }

    public void reset(){
        if (this.status != RESET) {
            this.status = RESET;
            timeline.pause();
            timeSeconds.set(startTime);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(startTime + 1),
                            new KeyValue(timeSeconds, 0)));
        }
    }

    public void setFinishedListener(EventHandler e) {
        this.timeline.setOnFinished(e);
    }

}
