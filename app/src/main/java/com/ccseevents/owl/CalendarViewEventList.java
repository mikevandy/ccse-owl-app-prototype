package com.ccseevents.owl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

public class CalendarViewEventList extends AppCompatActivity {

    public ImageButton ListView;
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view_event_list);
        CalendarView calendar = (CalendarView)findViewById(R.id.calendarView2);
        ImageButton ListView = (ImageButton)findViewById(R.id.ListBttn);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(CalendarViewEventList.this, "Date Changed", Toast.LENGTH_SHORT).show();
            }
        });

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