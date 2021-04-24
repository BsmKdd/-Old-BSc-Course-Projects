package com.example.bassamproject;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class examListAdapter extends RecyclerView.Adapter<examListAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback
{

    private static final int PERMISSION_STORAGE_CODE = 1000;
    private List<examItem> examItems;

    public examListAdapter(ArrayList<examItem> examItems, Context context)
    {
        this.examItems = examItems;
        this.context = context;
    }

    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_list_row, parent, false);
        return new ViewHolder(v);
    }


    examItem examItem;
    DownloadManager downloadManager;
    String downloadTag;

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //Change this
        examItem = examItems.get(position);
        holder.textViewSource.setText(examItem.getUniversity() + "  " + examItem.getMajor());
        holder.textViewTime.setText(examItem.getYear() + "  " + examItem.getSemester()
                                    + "  " + examItem.getNumber());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(examItem.getUploadDate());
        holder.textViewUploadDate.setText("Uploaded: " + date);

        holder.downloadButton.setTag(examItem.getFileDir());

        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Activity act = (Activity)v.getContext();
                downloadManager = (DownloadManager)v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                downloadTag = v.getTag().toString();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(v.getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        ActivityCompat.requestPermissions(act, permissions, PERMISSION_STORAGE_CODE);

                    } else {
                        startDownloading();
                    }
                } else {
                    startDownloading();
                }

            }
        });


    }

    private void startDownloading()
    {
        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
        String url = "https://bassampreviouses.000webhostapp.com/uploads/" + downloadTag;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(downloadTag);
        request.setDescription("Downloading file...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+System.currentTimeMillis());

        downloadManager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_STORAGE_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    startDownloading();
                } else {
                    Toast.makeText(context, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public int getItemCount()
    {
        return examItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView textViewSource;
        TextView textViewTime;
        TextView textViewUploadDate;
        ImageButton downloadButton;

        ViewHolder(View itemView)
        {
            super(itemView);
            textViewSource = (TextView)itemView.findViewById(R.id.textViewSource);
            textViewTime = (TextView)itemView.findViewById(R.id.textViewTime);
            textViewUploadDate= (TextView)itemView.findViewById(R.id.textViewUploadDate);
            downloadButton = (ImageButton)itemView.findViewById(R.id.downloadButton);
            downloadButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {

        }
    }


    public void filterList(ArrayList<examItem> filteredList)
    {
        this.examItems = filteredList;
        notifyDataSetChanged();
    }
}
