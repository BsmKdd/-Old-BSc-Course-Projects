package com.example.mygym;

import java.util.Date;

public class previousWorkoutItem
{
    private int workoutID;
    private String workoutName;
    private Date finishedOn;
    private int movesCount;

    public previousWorkoutItem(int workoutID, String workoutName, Date finishedOn, int movesCount)
    {
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.finishedOn = finishedOn;
        this.movesCount = movesCount;
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public void setMovesCount(int movesCount) {
        this.movesCount = movesCount;
    }

    public Date getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(Date finishedOn) {
        this.finishedOn = finishedOn;
    }
}