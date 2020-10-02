package com.ccseevents.owl;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView myTextView;

    public MyViewHolder(final View itemView) {
        super(itemView);
        myTextView = (TextView) itemView.findViewById(R.id.my_text_view);
    }

    public void bindData(final MyViewModel viewModel) {
        myTextView.setText(viewModel.getMyText());
    }
}
