package Gym;

import java.util.Date;

public class WorkoutEntry {
    private Date date;
    private String exercise;
    private int sets;
    private int reps;

    public WorkoutEntry(Date date, String exercise, int sets, int reps) {
        this.date = date;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
    }

    public Date getDate() {
        return date;
    }

    public String getExercise() {
        return exercise;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }
}
