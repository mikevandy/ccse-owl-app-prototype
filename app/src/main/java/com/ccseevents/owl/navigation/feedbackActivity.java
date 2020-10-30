package com.ccseevents.owl.navigation;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ccseevents.owl.CommentDialogActivity;
import com.ccseevents.owl.R;

public class feedbackActivity extends AppCompatActivity implements CommentDialogActivity.CommentDialogListener  {
    public Button addCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        //ADD Comment button
        addCommentButton = findViewById(R.id.addCommentButton);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });




    }

    public void openDialog() {
        CommentDialogActivity commentDialogActivity = new CommentDialogActivity();
        commentDialogActivity.show(getSupportFragmentManager(), "comment dialog");
    }

    @Override
    public void applyTexts(String name, String comment) {

    }
}