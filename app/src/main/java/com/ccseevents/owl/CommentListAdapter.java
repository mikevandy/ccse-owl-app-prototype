package com.ccseevents.owl;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommentListAdapter extends ArrayAdapter<Comment> {

    private Context mContext;
    int mResource;
    public CommentListAdapter(Context context, int resource, @NonNull ArrayList<Comment> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String comment = getItem(position).getComment();
        String date = getItem(position).getDate();

        Comment com = new Comment(name, comment, date);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView textViewName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView textViewComment = (TextView) convertView.findViewById(R.id.textViewComment);
        TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);

        textViewName.setText(name);
        textViewComment.setText(comment);
        textViewDate.setText(date);

        textViewName.setTypeface(null, Typeface.BOLD);

        return convertView;
    }
}
