package misc;

import alerts.Toast;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tables.Entry;
import tables.Exercise;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class DBManager {
    private static DBManager ourInstance = new DBManager();

    public static DBManager getInstance() {
        return ourInstance;
    }

    EntityManagerFactory emf;
    EntityManager em;


    private DBManager() {
        emf = Persistence.createEntityManagerFactory("bubbleWorkoutPU");
        em = emf.createEntityManager();
    }

    public EntityManager getEntityManger(){
        return em;
    }

    public List<Exercise> findAll() {
        Query q = em.createQuery("select e from Exercise e WHERE :a NOT IN elements(e.athletes) ");
        q.setParameter("a", Session.getInstance().getAthlete());
        return q.getResultList();
    }

    public void update(Object e) throws Exception {
        EntityManager em = DBManager.getInstance().getEntityManger();
        EntityTransaction utx = em.getTransaction();
        utx.begin();
        em.persist(e);
        utx.commit();
    }

    public void saveEntry(Exercise exercise, long workoutUnits, Pane primaryPane){
        Entry newEntry = new Entry();
        newEntry.setEntryAthlete(Session.getInstance().getAthlete());
        newEntry.setWorkoutUnits(workoutUnits);
        newEntry.setEntryDate(new Date());
        newEntry.setEntryExercise(exercise);

        Session.getInstance().getAthlete().addEntry(newEntry);
        exercise.addEntry(newEntry);

        try {
            this.update(newEntry);
            this.update(exercise);
            this.update(Session.getInstance().getAthlete());
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Exercise successfully completed!", 2000, 200, 200);
        } catch (Exception ex) {
            Toast.makeText((Stage) primaryPane.getScene().getWindow(), "Progress could not be added to database!", 2000, 200, 200);
        }
    }


}
