package com.example.mygym;

import android.graphics.Bitmap;

public class personalTrainerItem
{
    private int id;
    private String name;
    private String description;
    private String email;
    private String phone;
    private String title;
    private String image1;
    private String image2;
    private boolean shown;

    public personalTrainerItem(int id, String name, String description, String email, String phone, String title, String image1, String image2) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.title = title;
        this.image1 = "https://gym-senior-2020.000webhostapp.com/TrainerImages/Profiles/" + image1;
        this.image2 = "https://gym-senior-2020.000webhostapp.com/TrainerImages/Profiles/" + image2;
        this.shown = false;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }
}
