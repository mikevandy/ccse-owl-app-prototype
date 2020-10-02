package com.ccseevents.owl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
    }
    public void goToEventListDetail(View view) {
        Intent intent = new Intent(this, EventListDetailActivity.class);

        startActivity(intent);
    }
}