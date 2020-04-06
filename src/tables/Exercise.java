package tables;


import enums.MeasureType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Exercise")
public class Exercise implements Comparable<Exercise> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String exerciseName;

    @Enumerated(EnumType.STRING)
    @Column
    private MeasureType measure;

    @Column
    private long incrementAmount;

    @Column
    private long startValue;

    @Column
    private String unitName;

    @Column
    private String iconKey;

    @OneToMany(
            mappedBy = "entryExercise",
            cascade = CascadeType.MERGE,
            fetch=FetchType.LAZY
    )
    private List<Entry> entries = new ArrayList<>();

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            mappedBy = "athleteExercises",
            fetch=FetchType.LAZY
    )
    private List<Athlete> athletes = new ArrayList<>();

    public Exercise() {

    }

    public String getIconKey() {
        return iconKey;
    }

    public void setIconKey(String iconKey) {
        this.iconKey = iconKey;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public MeasureType getMeasure() {
        return measure;
    }

    public void setMeasure(MeasureType measure) {
        this.measure = measure;
    }

    public long getIncrementAmount() {
        return incrementAmount;
    }

    public void setIncrementAmount(long incrementAmount) {
        this.incrementAmount = incrementAmount;
    }

    public long getStartValue() {
        return startValue;
    }

    public void setStartValue(long startValue) {
        this.startValue = startValue;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public int compareTo(Exercise o) {
        return this.exerciseName.compareTo(o.exerciseName);
    }
}
