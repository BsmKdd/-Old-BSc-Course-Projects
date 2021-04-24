package com.example.bassamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class universityAdapter extends ArrayAdapter<universityItem>
{
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.university_spinner_row, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.spinnerTV);

        universityItem currentItem = getItem(position);

        if(currentItem != null) textViewName.setText(currentItem.getmUniversityName());

        return convertView;

    }

    public universityAdapter(Context context, ArrayList<universityItem> universityList)
    {
        super(context, 0, universityList);
    }
}
