package tables;

import enums.Language;
import misc.DBManager;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Athlete")
public class Athlete {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String username;

    @Column
    @Convert(converter = Encrypter.class)
    private String password;

    @Column
    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column
    private Language language;

    @OneToMany(
            mappedBy = "entryAthlete",
            cascade = CascadeType.MERGE,
            fetch=FetchType.LAZY
    )
    private List<Entry> entries = new ArrayList<>();

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            fetch=FetchType.LAZY
    )
    @JoinTable(
            name = "athlete_exercise",
            joinColumns = {@JoinColumn(name = "athlete_id")},
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<Exercise> athleteExercises = new ArrayList<>();

    public Athlete() {

    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Exercise> getExercises() {
            Collections.sort(athleteExercises);
            return athleteExercises;
    }

    public void addExercise(Exercise e) {
        this.athleteExercises.add(e);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Entry getLatestEntry(Exercise exercise){
        Entry entry = null;
        for (Entry e : this.entries) {
            if (e.getEntryExercise().getId() == exercise.getId()) {
                if (entry == null) {
                    entry = e;
                } else if (entry.getEntryDate().compareTo(e.getEntryDate()) < 0) {
                    entry = e;
                }
            }
        }
        return entry;
    }

}
