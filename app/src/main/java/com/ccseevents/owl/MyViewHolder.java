package com.ccseevents.owl;

import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView myDayOfTheWeek;
    private TextView myDayMonth;
    private TextView myTitle;
    private TextView myDateTime;
    private ToggleButton myFavorite;

    public MyViewHolder(final View itemView) {
        super(itemView);
        myDayOfTheWeek = (TextView) itemView.findViewById(R.id.eventDateView);
        myDayMonth = (TextView) itemView.findViewById(R.id.eventDayMonthView);
        myTitle = (TextView) itemView.findViewById(R.id.eventTitle);
        myDateTime = (TextView) itemView.findViewById(R.id.eventDateTime);
        myFavorite = (ToggleButton) itemView.findViewById(R.id.toggleFavorite);
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
}
