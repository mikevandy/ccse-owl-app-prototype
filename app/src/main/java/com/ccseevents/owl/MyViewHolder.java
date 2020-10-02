package com.ccseevents.owl;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
//    private TextView myDateBox;
    private TextView myFirstLine;
    private TextView mySecondLine;

    public MyViewHolder(final View itemView) {
        super(itemView);
//        myDateBox = (TextView) itemView.findViewById(R.id.dateBox);
        myFirstLine = (TextView) itemView.findViewById(R.id.secondLine);
        mySecondLine = (TextView) itemView.findViewById(R.id.firstLine);
    }

    public void bindData(final MyViewModel viewModel) {
//        myDateBox.setText(viewModel.getDayOfWeek());
        myFirstLine.setText(viewModel.getDescription());
        mySecondLine.setText(viewModel.getDescription());
    }
}
