package com.example.bassamproject;

import java.util.Date;

public class examItem
{
    private String fileDir;
    private String university;
    private String school;
    private String major;
    private int year;
    private String semester;
    private String number;
    private Date uploadDate;

    public examItem(String fileDir, String university, String school, String major, int year, String semester, String number, Date uploadDate) {
        this.fileDir = fileDir;
        this.university = university;
        this.school = school;
        this.major = major;
        this.year = year;
        this.semester = semester;
        this.number = number;
        this.uploadDate = uploadDate;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
