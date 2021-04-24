package com.example.bassamproject;

public class universityItem
{
    private String mUniversityName;
    private String mUniversityValue;

    public universityItem(String universityName, String universityValue)
    {
        mUniversityName = universityName;
        mUniversityValue = universityValue;
    }

    public String getmUniversityName()
    {
        return mUniversityName;
    }

    public String getmUniversityValue()
    {
        return mUniversityValue;
    }

    @Override
    public String toString() {
        return mUniversityName;
    }
}
