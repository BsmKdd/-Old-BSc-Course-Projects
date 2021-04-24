package com.example.mygym;

import java.util.Date;

public class workoutItem
{
    private int workoutID;
    private String workoutName;
    private String workoutImage;
    private String assignedBy;
    private Date assignedDate;
    private int movesCount;

    public workoutItem(int workoutID, String workoutName, String workoutImage, String assignedBy, Date assignedDate, int movesCount)
    {
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.workoutImage = workoutImage;
        this.assignedBy = assignedBy;
        this.assignedDate = assignedDate;
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

    public String getWorkoutImage() {
        return workoutImage;
    }

    public void setWorkoutImage(String workoutImage) {
        this.workoutImage = workoutImage;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public void setMovesCount(int movesCount) {
        this.movesCount = movesCount;
    }



}