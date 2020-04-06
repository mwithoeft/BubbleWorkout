package tables;

import misc.DBManager;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Entry")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @Temporal(TemporalType.DATE)
    private Date entryDate;

    @Column
    private long workoutUnits;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch=FetchType.LAZY
    )
    @JoinColumn(name = "Exercise")
    private Exercise entryExercise;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch=FetchType.LAZY
    )
    @JoinColumn(name = "Athlete")
    private Athlete entryAthlete;


    public Entry() {

    }

    public Exercise getEntryExercise() {
        return entryExercise;
    }

    public void setEntryExercise(Exercise entryExercise) {
        this.entryExercise = entryExercise;
    }

    public Athlete getEntryAthlete() {
        return entryAthlete;
    }

    public void setEntryAthlete(Athlete entryAthlete) {
        this.entryAthlete = entryAthlete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public long getWorkoutUnits() {
        return workoutUnits;
    }

    public void setWorkoutUnits(long workoutUnits) {
        this.workoutUnits = workoutUnits;
    }

}
