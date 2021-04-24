package com.example.mygymcoach;

public class moveItem
{
    private int moveId;
    private int machineId;
    private boolean isSelected;
    private String moveName;
    private String moveImage1;
    private String moveImage2;
    private String moveGif;
    private String machineImage;
    private String moveDescription;
    private String muscleGroup1;

    private int floor;
    private String section;
    private String machineName;

    public moveItem(int moveId, int machineId, String moveName, String moveImage1, String moveImage2, String moveGif, String machineImage, String moveDescription, String muscleGroup1, int floor, String section, String machineName)
    {
        this.moveId = moveId;
        this.machineId = machineId;
        this.moveGif = moveGif;
        this.machineImage = machineImage;
        this.floor = floor;
        this.section = section;
        this.machineName = machineName;
        this.isSelected = false;
        this.moveName = moveName;
        this.moveImage1 = moveImage1;
        this.moveImage2 = moveImage2;
        this.moveDescription = moveDescription;
        this.muscleGroup1 = muscleGroup1;
    }

    public int getMoveId() {
        return moveId;
    }

    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getMoveName() {
        return moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public String getMoveImage1() {
        return moveImage1;
    }

    public void setMoveImage1(String moveThumbnail) {
        this.moveImage2 = moveThumbnail;
    }

    public String getMoveDescription() {
        return moveDescription;
    }

    public void setMoveDescription(String moveDescription) {
        this.moveDescription = moveDescription;
    }

    public String getMuscleGroup1() {
        return muscleGroup1;
    }

    public void setMuscleGroup1(String muscleGroup1) {
        this.muscleGroup1 = muscleGroup1;
    }

    public String getMoveImage2() {
        return moveImage2;
    }

    public void setMoveImage2(String moveImage2) {
        this.moveImage2 = moveImage2;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getMoveGif() {
        return moveGif;
    }

    public void setMoveGif(String moveGif) {
        this.moveGif = moveGif;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getMachineImage() {
        return machineImage;
    }

    public void setMachineImage(String machineImage) {
        this.machineImage = machineImage;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}