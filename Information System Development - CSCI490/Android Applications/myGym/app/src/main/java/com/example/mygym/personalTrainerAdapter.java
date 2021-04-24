package com.example.mygym;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class personalTrainerAdapter extends RecyclerView.Adapter<personalTrainerAdapter.ViewHolder> implements View.OnClickListener
{
    private List<personalTrainerItem> personalTrainerItems;
    private Context context;

    public personalTrainerAdapter(List<personalTrainerItem> personalTrainerItems, Context context) {
        this.personalTrainerItems = personalTrainerItems;
        this.context = context;
    }

    @NonNull
    @Override
    public personalTrainerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_listing, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final personalTrainerAdapter.ViewHolder holder, int position) {

        personalTrainerItem personalTrainerItem = personalTrainerItems.get(position);


        Glide.with(context).load(personalTrainerItem.getImage1()).placeholder(R.drawable.personplaceholder)
                .into(holder.trainerImageBack);

        Glide.with(context).load(personalTrainerItem.getImage2()).placeholder(R.drawable.personplaceholder)
                .into(holder.trainerImageCircle);


        holder.trainerName.setText(personalTrainerItem.getName());
        holder.trainerTitle.setText(personalTrainerItem.getTitle());
        holder.trainerDescription.setText(personalTrainerItem.getDescription());
        holder.trainerPhone.setText(personalTrainerItem.getPhone());
        holder.trainerMail.setText(personalTrainerItem.getEmail());

        holder.callTrainerLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Uri phoneNum = Uri.parse("tel:"+holder.trainerPhone.getText().toString());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneNum);

                if(callIntent.resolveActivity(context.getPackageManager()) != null)
                    context.startActivity(callIntent);
                else Toast.makeText(context, "This action can not be performed.", Toast.LENGTH_SHORT).show();
            }
        });

        holder.mailTrainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String[] email = new String[]{holder.trainerMail.getText().toString()};

                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                mailIntent.putExtra(Intent.EXTRA_EMAIL, email);
                if (mailIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mailIntent);
                }


            }
        });

        boolean isShown = personalTrainerItem.isShown();
        holder.hiddenLayout.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount()
    {
        return personalTrainerItems.size();
    }

    @Override
    public void onClick(View v)
    {

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public RelativeLayout showLayout;
        public RelativeLayout hiddenLayout;
        public ImageView trainerImageBack;
        public ImageView trainerImageCircle;
        public TextView trainerName;
        public TextView trainerTitle;
        public TextView trainerDescription;
        public TextView trainerPhone;
        public TextView trainerMail;
        public RelativeLayout callTrainerLayout;
        public RelativeLayout mailTrainerLayout;



        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            showLayout = itemView.findViewById(R.id.trainerShown);
            hiddenLayout = itemView.findViewById(R.id.trainerHidden);
            trainerImageBack = itemView.findViewById(R.id.hireTrainerImage);
            trainerImageCircle = itemView.findViewById(R.id.hireTrainerImageCircle);
            trainerName = itemView.findViewById(R.id.hireTrainerName);
            trainerTitle = itemView.findViewById(R.id.hireTrainerTitle);
            trainerDescription = itemView.findViewById(R.id.trainerDescription);
            trainerPhone = itemView.findViewById(R.id.trainerPhone);
            trainerMail = itemView.findViewById(R.id.trainerMail);
            callTrainerLayout = itemView.findViewById(R.id.callTrainerLayout);
            mailTrainerLayout = itemView.findViewById(R.id.mailTrainerLayout);


            showLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                   personalTrainerItem personalTrainerItem = personalTrainerItems.get((getAdapterPosition()));
                   personalTrainerItem.setShown(!personalTrainerItem.isShown());
                   notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
