package misc;

import tables.Athlete;
import tables.Exercise;

import java.util.Collections;
import java.util.List;


public class Session {
    private static Session session = new Session();
    public static Session getInstance() {
        return session;
    }

    private Athlete athlete = null;
    private List<Exercise> exercises;

    private Session() {

    }

    public void startSession(Athlete a) {
        this.athlete = a;
        fetchExercises();
    }

    private void fetchExercises(){
        this.exercises = DBManager.getInstance().findAll();
        Collections.sort(this.exercises);
    }

    public Athlete getAthlete(){
        return this.athlete;
    }
    public void endSession() {
        this.athlete = null;
    }

    public List<Exercise> getExercises(){
        return this.exercises;
    }

    public void refreshExercises(){
        fetchExercises();
    }


}
