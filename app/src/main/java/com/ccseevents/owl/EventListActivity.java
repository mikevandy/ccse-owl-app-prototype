package com.ccseevents.owl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EventListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
    }
    public void goToEventListDetail(View view) {
        Intent intent = new Intent(this, EventListDetailActivity.class);

        startActivity(intent);
    }
}