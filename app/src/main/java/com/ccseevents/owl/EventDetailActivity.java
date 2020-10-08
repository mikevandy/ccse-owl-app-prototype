package com.ccseevents.owl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailActivity extends AppCompatActivity {
    public MyEventsDatabaseHelper myeventDB = new MyEventsDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.adToolbar);
        final Button FavoriteButton = (Button)findViewById(R.id.btn_favorite);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Bundle bundle = getIntent().getExtras();
        //Check whether event has already been added to My Events and adjust button text
        int eID = bundle.getInt("EVENTID");
        boolean btnfavorited = myeventDB.existsMyEvents(eID);
        if (btnfavorited) {
            FavoriteButton.setText("Remove from My Events");
        }

        // TITLE
        String titleValue = bundle.getString("TITLE");
        TextView titleTextView = (TextView)findViewById(R.id.titleText);
        titleTextView.setText(titleValue);

        // HOST
        String hostValue = bundle.getString("HOST");
        TextView hostTextView = (TextView)findViewById(R.id.hostedByText);
        hostTextView.setText(hostValue);

        // DATE
        String dayValue = bundle.getString("DAY");
        String monthValue = bundle.getString("MONTH");
        String yearValue = bundle.getString("YEAR");
        TextView dateTextView = (TextView)findViewById(R.id.dateText);
        dateTextView.setText(monthValue + " " + dayValue + ", " + yearValue);

        // TIME
        String fromValue = bundle.getString("TIMEFROM");
        String toValue = bundle.getString("TIMETO");
        TextView timeTextView = (TextView)findViewById(R.id.timeText);
        timeTextView.setText(fromValue + " to " + toValue);

        String locationValue = bundle.getString("LOCATION");
        TextView locationTextView = (TextView)findViewById(R.id.locationText);
        locationTextView.setText(locationValue);

        // DESCRIPTION
        String descriptionValue = bundle.getString("DESCRIPTION");
        TextView descriptionTextView = (TextView)findViewById(R.id.descriptionText);
        descriptionTextView.setText(descriptionValue);

        FavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int eventID = bundle.getInt("EVENTID");
                boolean favorited = myeventDB.existsMyEvents(eventID);
                if (favorited) {
                    boolean isDeleted = myeventDB.deleteMyEvents(eventID);
                    if (isDeleted) {
                        FavoriteButton.setText("Add to My Events");
                    }
                    else{
                        Toast.makeText(EventDetailActivity.this, "Failed to Remove", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    boolean isInserted = myeventDB.insertMyEvents(eventID);
                    if (isInserted) {
                        FavoriteButton.setText("Remove from My Events");
                    }
                    else{
                        Toast.makeText(EventDetailActivity.this, "Failed to Add", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}