package com.ccseevents.owl;

import android.os.Build;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
    private TextView myDayOfTheWeek;
    private TextView myDayMonth;
    private TextView myTitle;
    private TextView myDateTime;
    private ToggleButton myFavorite;
    private boolean isHidden;
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
                    int position = getAbsoluteAdapterPosition();
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
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.toggleFav(position);
                    }
                }
            }
        });

        itemView.setOnCreateContextMenuListener(this);
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
        isHidden = viewModel.getHidden();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        if(isHidden){
            contextMenu.add(this.getAbsoluteAdapterPosition(),0,0,"Unhide Event");
        }else{
            contextMenu.add(this.getAbsoluteAdapterPosition(),0,0,"Hide Event");
        }
    }
}
