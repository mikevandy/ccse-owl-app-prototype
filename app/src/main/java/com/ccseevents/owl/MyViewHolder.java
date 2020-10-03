package com.ccseevents.owl;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView myDayOfTheWeek;
    private TextView myDayMonth;
    private TextView myTitle;
    private TextView myDateTime;

    public MyViewHolder(final View itemView) {
        super(itemView);
        myDayOfTheWeek = (TextView) itemView.findViewById(R.id.eventDateView);
        myDayMonth = (TextView) itemView.findViewById(R.id.eventDayMonthView);
        myTitle = (TextView) itemView.findViewById(R.id.eventTitle);
        myDateTime = (TextView) itemView.findViewById(R.id.eventDateTime);
    }

    public void bindData(final MyViewModel viewModel) {
        myDayOfTheWeek.setText(viewModel.getDayOfWeek());
        myDayMonth.setText(viewModel.getDay() + " " + viewModel.getMonth());
        myTitle.setText(viewModel.getTitle());
        myDateTime.setText(viewModel.getMonth() + " " + viewModel.getDay() + " @ " + viewModel.getFromTime());
    }
}
