package com.ccseevents.owl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CalendarViewEventList extends AppCompatActivity {

    public ImageButton ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view_event_list);

        ImageButton ListView = (ImageButton)findViewById(R.id.ListBttn);

        ListView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(CalendarViewEventList.this, EventListActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

}