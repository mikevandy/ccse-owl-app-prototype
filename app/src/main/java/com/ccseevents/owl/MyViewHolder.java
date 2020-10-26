package com.ccseevents.owl;

import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    private TextView myDayOfTheWeek;
    private TextView myDayMonth;
    private TextView myTitle;
    private TextView myDateTime;
    private ToggleButton myFavorite;

    public MyViewHolder(final View itemView, final MyAdapter.OnItemClickListener listener) {
        super(itemView);
        myDayOfTheWeek = (TextView) itemView.findViewById(R.id.eventDateView);
        myDayMonth = (TextView) itemView.findViewById(R.id.eventDayMonthView);
        myTitle = (TextView) itemView.findViewById(R.id.eventTitle);
        myDateTime = (TextView) itemView.findViewById(R.id.eventDateTime);
        myFavorite = (ToggleButton) itemView.findViewById(R.id.toggleFavorite);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
        myFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.toggleFav(position);
                    }
                }
            }
        });

        //Long Press
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "Position is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bindData(final MyViewModel viewModel) {
        myDayOfTheWeek.setText(viewModel.getDayOfWeek());
        myDayMonth.setText(viewModel.getDay() + " " + viewModel.getMonth());
        myTitle.setText(viewModel.getTitle());
        if(viewModel.getFromTime().equals("12:00AM")){
            myDateTime.setText(viewModel.getMonth() + " " + viewModel.getDay() + " - All Day");
        }else {
            myDateTime.setText(viewModel.getMonth() + " " + viewModel.getDay() + " @ " + viewModel.getFromTime());
        }
        myFavorite.setChecked(viewModel.getFavorite());

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View view) {
        return true;
    }
}
